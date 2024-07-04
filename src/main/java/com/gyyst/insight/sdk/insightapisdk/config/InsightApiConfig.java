package com.gyyst.insight.sdk.insightapisdk.config;

import com.gyyst.insight.sdk.insightapisdk.client.InsightApiClient;
import com.gyyst.insight.sdk.insightapisdk.model.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
@ConfigurationProperties("insight.config")
@ComponentScan
public class InsightApiConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public InsightApiClient insightApiClient() {
        Auth auth = Auth.create(accessKey, secretKey);
        return new InsightApiClient(auth);
    }
}
