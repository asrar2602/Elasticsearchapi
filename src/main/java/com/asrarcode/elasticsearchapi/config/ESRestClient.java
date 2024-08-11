package com.asrarcode.elasticsearchapi.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
//@ConfigurationProperties("es")
@Getter
@Setter
public class ESRestClient {
    private static final Logger log = LoggerFactory.getLogger(ESRestClient.class);
    @Value("${elastic.url}")
    private String url;

    @Value("${elastic.apikey}")
    private String apiKey;

    @Bean
    public ElasticsearchClient getElasticSearchClient() {

        RestClient restClient = RestClient
                .builder(HttpHost.create(url))
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", "ApiKey " + apiKey)
                })
                .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        log.info("Elastic Connection Established.!");
        log.info("Elastic is Running?: {}", restClient.isRunning());
        // And create the API client
        return new ElasticsearchClient(transport);
    }

}
