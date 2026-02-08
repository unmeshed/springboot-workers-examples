package io.unmeshed.sdk.samples.config;

import io.unmeshed.client.ClientConfig;
import io.unmeshed.client.UnmeshedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnmeshedClientConfig {

    @Value("${unmeshed.engine.url}")
    private String unmeshedEngineUrl;

    @Value("${unmeshed.auth.id}")
    private String unmeshedAuthId;

    @Value("${unmeshed.auth.token}")
    private String unmeshedAuthToken;

    @Bean
    public UnmeshedClient unmeshedClient() {
        ClientConfig clientConfig = ClientConfig.builder(unmeshedAuthId, unmeshedAuthToken)
                .baseUrl(unmeshedEngineUrl)
                .workRequestBatchSize(200)
                .initialDelayMillis(20)
                .stepTimeoutMillis(1000L * 60 * 60 * 24 * 365)
                .build();
        return new UnmeshedClient(clientConfig);
    }

}
