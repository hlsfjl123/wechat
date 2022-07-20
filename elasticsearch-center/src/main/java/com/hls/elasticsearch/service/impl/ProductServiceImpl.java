package com.hls.elasticsearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hls.elasticsearch.entity.Product;
import com.hls.elasticsearch.mapper.ProductMapper;
import com.hls.elasticsearch.service.ProductService;
import com.hls.elasticsearch.util.HighLevelUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: User-XH251
 * @Date: 2022/7/18 17:22
 */
@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    HighLevelUtil highLevelUtil;

    @Override
    public List<Product> search() throws IOException {
        List<Product> productList = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        CollapseBuilder collapse = new CollapseBuilder("id");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false)
                .field("description")
                .preTags("<span style='color:red;'>")
                .postTags("</span>");
        searchSourceBuilder.query(QueryBuilders.termQuery("description", "了"))
                .from(0)
                .size(2)
                .sort("id", SortOrder.DESC)
                //去重
                .collapse(collapse)
                .highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println("es原始结果为" + JSON.toJSONString(sourceAsMap));
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("description")) {
                System.out.println("高亮结果为" + highlightFields.get("description").fragments()[0]);
                Text description = highlightFields.get("description").fragments()[0];
                sourceAsMap.put("description", description.toString());
            }
            System.out.println("es高亮结果组装后结果为" + JSON.toJSONString(sourceAsMap));
            Product product = JSON.parseObject(JSON.toJSONString(sourceAsMap),Product.class);
            productList.add(product);
        }
        return productList;
    }

    @Override
    public List<Product> search2() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        CollapseBuilder collapse = new CollapseBuilder("id");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false)
                .field("description")
                .preTags("<span style='color:red;'>")
                .postTags("</span>");
        searchSourceBuilder.query(QueryBuilders.termQuery("description", "了"))
                .from(0)
                .size(2)
                .sort("id", SortOrder.DESC)
                //去重
                .collapse(collapse)
                .highlighter(highlightBuilder);
        SearchResponse searchResponse = highLevelUtil.searchHighlight("product", searchSourceBuilder, new String[]{"description"});
        List<Product> result = highLevelUtil.result(searchResponse.getHits(), Product.class);
        return result;
    }
}
