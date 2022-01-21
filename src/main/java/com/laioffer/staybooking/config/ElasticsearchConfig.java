package com.laioffer.staybooking.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

// client + objectMapping + operation + repositories

// 加了这个spring才可以注入
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    // value 可以从配置文件里面获得数据
    @Value("elasticsearch.address")
    private String elasticsearchAddress;

    @Value("${elasticsearch.username}")
    private String elasticsearch_username;

    @Value("${elasticsearch.password}")
    private String elasticsearch_password;

    // 连接elasticsearch
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticsearchAddress)
                .withBasicAuth(elasticsearch_username, elasticsearch_password)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
