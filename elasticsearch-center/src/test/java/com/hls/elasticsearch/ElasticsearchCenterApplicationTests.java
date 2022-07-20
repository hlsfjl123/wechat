package com.hls.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.hls.elasticsearch.entity.Product;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;

@SpringBootTest
class ElasticsearchCenterApplicationTests {

	@Autowired
	RestHighLevelClient restHighLevelClient;

	@Test
	void contextLoads() throws IOException {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest("product");
		createIndexRequest.mapping("", XContentType.JSON);
		CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
		createIndexResponse.isAcknowledged();
		restHighLevelClient.close();
	}

	@Test
	void insert() throws IOException {
		IndexRequest indexRequest = new IndexRequest("product");
		Product product = new Product();
		product.setId(7L);
		product.setPrice( 89.2);
		product.setCreateTime(new Date());
		product.setTitle("测试下文档插入");
		product.setDescription("侯林帅重新观看es");
		String jsonString = JSON.toJSONString(product);
		indexRequest.source(jsonString,XContentType.JSON);
		IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		System.out.println(indexResponse.status());
	}

}
