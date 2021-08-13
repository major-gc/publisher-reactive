package com.toy.publisher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        String connectionProviderName = "connectionProvider";
        int maxConnection = 100;
        int acquireTimeout = 5000;

        HttpClient httpClient = HttpClient.create(ConnectionProvider.builder(connectionProviderName)
                .maxConnections(maxConnection).build())
                .responseTimeout(Duration.ofMillis(acquireTimeout));

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                // 기본적으로 http 통신을 in-memory buffer 값이 256KB로 기본설정 되어 있습니다.
                // 이 제약 때문에 256KB보다 큰 HTTP 메시지를 처리하려고 하면 DataBufferLimitException 발생하기때문에 늘려줘야한다.
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50))
                .build();

        exchangeStrategies
                .messageWriters().stream()
                .filter(LoggingCodecSupport.class::isInstance)
                .forEach(httpMessageWriter -> ((LoggingCodecSupport) httpMessageWriter).setEnableLoggingRequestDetails(true));

        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        clientRequest -> {
                            return Mono.just(clientRequest);
                        }
                ))
                .filter(ExchangeFilterFunction.ofResponseProcessor(
                        clientResponse -> {
                            return Mono.just(clientResponse);
                        }
                ))
                .clientConnector(new ReactorClientHttpConnector(httpClient.wiretap(true)))
                .build();
    }
}
