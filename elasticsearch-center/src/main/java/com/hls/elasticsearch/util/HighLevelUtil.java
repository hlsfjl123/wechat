package com.hls.elasticsearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: User-XH251
 * @Date: 2022/7/18 15:49
 */
@Slf4j
@Component
public class HighLevelUtil {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 单体保存
     *
     * @param indexName 索引名字
     * @param source    json类型实体类名字
     */
    public void save(String indexName,String type,String idName,String source) {
        if (StringUtils.hasText(source)) {
            return;
        }
        try {
            IndexRequest indexRequest = new IndexRequest(indexName);
            indexRequest.id(JSON.parseObject(source).get(idName).toString()).source(source, XContentType.JSON);
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("单体保存到es失败", e);
        }
    }

    /**
     * 批量保存
     *
     * @param indexName
     * @param idName
     * @param sourceList
     */
    public void saveBatch(String indexName, String idName, List<Object> sourceList) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return;
        }
        try {
            BulkRequest bulkRequest = new BulkRequest();
            for (Object object : sourceList) {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
                if (jsonObject.get(idName) == null) {
                    continue;
                }
                UpdateRequest updateRequest = new UpdateRequest(indexName,jsonObject.get(idName).toString());
                updateRequest.doc(JSON.toJSONString(object), XContentType.JSON);
                bulkRequest.add(updateRequest);
            }
            restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("批量保存到es失败", e);
        }

    }

    /**
     * 根据id删除
     *
     * @param indexName
     * @param id
     */
    public void deleteById(String indexName, String id) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es删除失败", e);
        }
    }


    /**
     * es删除
     *
     * @param indexName
     * @param queryBuilder
     */
    public void delete(String indexName, QueryBuilder queryBuilder) {
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(indexName);
        deleteByQueryRequest.setQuery(queryBuilder);
        try {
            restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es删除失败", e);
        }
    }

    /**
     * es查询
     *
     * @param indexName
     * @param sourceBuilder
     * @return
     */
    public SearchResponse search(String indexName, SearchSourceBuilder sourceBuilder) {
        try {
            SearchRequest searchRequest = new SearchRequest(indexName);
            searchRequest.source(sourceBuilder);
            return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es查询失败", e);
            return null;
        }
    }

    /**
     * 高亮搜搜
     *
     * @param indexName
     * @param sourceBuilder
     * @param highFields
     * @return
     */
    public SearchResponse searchHighlight(String indexName, SearchSourceBuilder sourceBuilder, String[] highFields) {
        SearchResponse searchResponse = null;
        try {
            SearchRequest searchRequest = new SearchRequest(indexName);
            searchRequest.source(sourceBuilder);
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //处理返回结果高亮
            searchResponse = highlight(searchResponse, highFields);
            return searchResponse;
        } catch (Exception e) {
            log.error("异常{}", e);
            return searchResponse;
        }
    }

    /**
     * 设置结果高亮
     *
     * @param searchResponse 查询结果
     * @param highFields     高亮字段集合
     * @return SearchHits
     */
    public static SearchResponse highlight(SearchResponse searchResponse, String[] highFields) {
        if (highFields.length == 0 || searchResponse.getHits().getTotalHits().value == 0) {
            return searchResponse;
        }
        //遍历结果
        for (SearchHit hit : searchResponse.getHits()) {
            Map<String, Object> source = hit.getSourceAsMap();
            //处理高亮片段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            for (String item : highFields) {
                HighlightField nameField = highlightFields.get(item);
                if (nameField != null) {
                    Text[] fragments = nameField.fragments();
                    StringBuilder nameTmp = new StringBuilder();
                    for (Text text : fragments) {
                        nameTmp.append(text);
                    }
                    //将高亮片段组装到结果中去
                    source.put(item, nameTmp.toString());
                }
            }
        }
        return searchResponse;
    }


    public  <T> List<T> result(SearchHits resultHits, Class<T> tClass) {
        SearchHits hits = resultHits;
        SearchHit[] searchHits = hits.getHits();
        List<T> respList = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            JSONObject jsonObject = new JSONObject(hit.getSourceAsMap());
            respList.add(JSONObject.toJavaObject(jsonObject, tClass));
        }
        return respList;
    }

}
