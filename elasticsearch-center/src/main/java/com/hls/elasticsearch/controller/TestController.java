package com.hls.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.hls.elasticsearch.annotation.TestAnnotation;
import com.hls.elasticsearch.entity.Product;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.GaussDecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filters;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

/**
 * @Author: User-XH251
 * @Date: 2022/7/18 11:32
 */
@RestController
@RequestMapping(value = "testes")
public class TestController {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @PostMapping(value = "insert")
    public void insert() throws IOException {
        Product product = new Product();
        product.setId(10L);
        product.setPrice(99.999);
        product.setCreateTime(new Date());
        product.setTitle("测试下文档插入");
        product.setDescription("侯林帅重新手撕elasticsearch");
        String jsonString = JSON.toJSONString(product);
        IndexRequest indexRequest = new IndexRequest("product");
        indexRequest.id(JSON.parseObject(jsonString).get("id").toString()).source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.status());
    }

    @PostMapping(value = "update")
    public void update() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("product", "Tf5rD4IBS56H91btBcBL");
        Product product = new Product();
        product.setPrice(100.00);
        String jsonString = JSON.toJSONString(product);
        updateRequest.doc(jsonString, XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());
    }


    @PostMapping(value = "delete")
    public void delete() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("product");
        deleteRequest.id("dOaT_4EB2kc62bLt-RHy");
        restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }

    @PostMapping(value = "getById")
    public void getById() throws IOException {
        GetRequest getRequest = new GetRequest("product");
        getRequest.id("Tf5rD4IBS56H91btBcBL");
        GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        Product product = JSON.parseObject(documentFields.getSourceAsString(), Product.class);
        System.out.println(product.toString());
    }

    @PostMapping(value = "searchAll")
    public void searchAll() throws IOException {
        //query(QueryBuilders.matchAllQuery());
        query(QueryBuilders.multiMatchQuery("了", "title", "description"));
    }


    private void query(QueryBuilder queryBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder
                .requireFieldMatch(false)
                .field("description")
                .preTags("<span style='color:red;'>")
                .postTags("</span>");

        searchSourceBuilder.query(queryBuilder)
                //(pagSize-1)*pageNum
                .from(0)
                .size(5)
                .sort("price", SortOrder.DESC)
                //返回指定字段
                //.fetchSource(new String[]{"title"}, new String[]{})
                .highlighter(highlightBuilder)
                //filter查询
                .postFilter(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("price").gte(1).lte(2)));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //总条数
        long value = searchResponse.getHits().getTotalHits().value;
        System.out.println("总条数" + value);
        //对象列表
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println("获取到得原始数据为" + JSON.toJSONString(sourceAsMap));
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("description")) {
                System.out.println("高亮结果为" + highlightFields.get("description").fragments()[0]);
                Text description = highlightFields.get("description").fragments()[0];
                sourceAsMap.put("description", description.toString());
            }
            Product product = JSON.parseObject(JSON.toJSONString(sourceAsMap), Product.class);
            System.out.println(product.toString());
        }
    }

    private void test(){
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //functionScore
//        FieldValueFactorFunctionBuilder fieldValueFactorFunctionBuilder = ScoreFunctionBuilders.fieldValueFactorFunction("price");
//        fieldValueFactorFunctionBuilder.missing(0);
//        searchSourceBuilder.query(QueryBuilders.functionScoreQuery(QueryBuilders.matchAllQuery(),
//                fieldValueFactorFunctionBuilder).maxBoost(10));
        //guass函数
        GaussDecayFunctionBuilder gaussDecayFunctionBuilder = ScoreFunctionBuilders.gaussDecayFunction("price","100","10","10",0.5);
        searchSourceBuilder.query(QueryBuilders.functionScoreQuery(QueryBuilders.matchAllQuery(),gaussDecayFunctionBuilder));
        searchRequest.source(searchSourceBuilder);
    }


    @PostMapping(value = "aggs")
    public void aggs() throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery())
                .aggregation(new SumAggregationBuilder("sum_price").field("price"));
        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Terms sumPrice = (Terms) search.getAggregations().asMap().get("sum_price");
        for (Terms.Bucket bucket : sumPrice.getBuckets()) {
            String bucketKey = (String) bucket.getKey();
            System.out.println("sum:"+bucketKey);
        }
    }

    /**
     * terms基于字段分类 group by
     * @throws IOException
     */
    @PostMapping(value = "aggsterms")
    public void aggsterms() throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder
                .aggregation(AggregationBuilders.terms("terms_price").field("price")
                        .subAggregation(new SumAggregationBuilder("sum_price").field("price")));
        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Terms termsPrice = (Terms) search.getAggregations().asMap().get("terms_price");
        termsPrice.getBuckets().sort(Comparator.comparing(Terms.Bucket::getDocCount));
        for (Terms.Bucket bucket : termsPrice.getBuckets()) {
            String bucketKey = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();
            Sum sumPrice = bucket.getAggregations().get("sum_price");
            System.out.println("sum:"+bucketKey);
            System.out.println("count:"+docCount);
            System.out.println("sumPrice2:"+sumPrice.getValue());
        }
    }
    @PostMapping(value = "minaggs")
    public void minaggs() throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder
                .aggregation(new MinAggregationBuilder("min_price").field("price"));
        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Min min_price = (Min) search.getAggregations().getAsMap().get("min_price");
        double value = min_price.getValue();
        System.out.println("最低价"+value);
    }



    @PostMapping(value = "maxaggs")
    public void maxaggs() throws IOException {
        System.out.println("1111111111111111111111111111111");
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder
                .aggregation(new MaxAggregationBuilder("max_price").field("price"));
        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Max max_price = (Max) search.getAggregations().getAsMap().get("max_price");
        double value = max_price.getValue();
        System.out.println("最高价"+value);
    }

    @PostMapping(value = "avgaggs")
    public void avgaggs() throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder
                .aggregation(new AvgAggregationBuilder("avg_price")
                        .field("price")
                        //没有值的0补齐
                        .missing(0));
        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Avg avg_price = (Avg) search.getAggregations().getAsMap().get("avg_price");
        double value = avg_price.getValue();
        System.out.println("平均价"+value);
    }

    @PostMapping(value = "filtersAggs")
    public void filterAggs() throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder
                .aggregation(new FiltersAggregationBuilder("filter_aggs",QueryBuilders.termQuery("title","测试下文档插入"))
                        .subAggregation(AggregationBuilders.sum("sum_price").field("price")));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Filters filter_aggs = (Filters) searchResponse.getAggregations().asMap().get("filter_aggs");
        for (Filters.Bucket bucket : filter_aggs.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();
            System.out.println(keyAsString);
            System.out.println(docCount);
            System.out.println("------------------------------------------------------------------");
            Sum sum_price = (Sum)bucket.getAggregations().get("sum_price");
            double value = sum_price.getValue();
            System.out.println(value);
        }
    }
    @TestAnnotation
    @PostMapping(value = "aggsmore")
    public void aggsmore() throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder
                .aggregation(AggregationBuilders.terms("terms_id").field("id").missing(0)
                        .subAggregation(AggregationBuilders.terms("terms_price").field("price")
                        .subAggregation(new MaxAggregationBuilder("max_price").field("price"))));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Terms terms_id =(Terms) searchResponse.getAggregations().asMap().get("terms_id");
        for (Terms.Bucket bucket : terms_id.getBuckets()) {
            Number keyAsNumber = bucket.getKeyAsNumber();
            long docCount = bucket.getDocCount();
            System.out.println(keyAsNumber);
            System.out.println(docCount);
            System.out.println("------------------------------------------------------------------");
            Terms terms_price = (Terms) bucket.getAggregations().asMap().get("terms_price");
            for (Terms.Bucket terms_priceBucket : terms_price.getBuckets()) {
                Number keyAsNumber1 = terms_priceBucket.getKeyAsNumber();
                long docCount1 = terms_priceBucket.getDocCount();
                Max max_price =(Max) terms_priceBucket.getAggregations().get("max_price");
                double value = max_price.getValue();
                System.out.println(keyAsNumber1);
                System.out.println(docCount1);
                System.out.println(value);
            }
        }
    }

}
