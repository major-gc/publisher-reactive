package com.toy.publisher.config;

import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SeibroWebClientConfig {
    private final WebClient webClient;
    private final String openApiSeibroBaseUrl;

    public SeibroWebClientConfig(WebClient webClient
            , @Value("${data.api.seibro.baseUrl}") String openApiSeibroBaseUrl) {
        this.webClient = webClient;
        this.openApiSeibroBaseUrl = openApiSeibroBaseUrl;
    }

    @Bean
    public WebClient openApiSeibroWebClient() {
        return this.webClient.mutate() // web Client 상속
                .baseUrl(openApiSeibroBaseUrl)
                .build();
    }
}
