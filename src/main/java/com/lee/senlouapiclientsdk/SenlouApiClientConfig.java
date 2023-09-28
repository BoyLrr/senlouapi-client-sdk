package com.lee.senlouapiclientsdk;

import com.lee.senlouapiclientsdk.client.SenlouApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("senlouapi.client")
@Data
@ComponentScan
public class SenlouApiClientConfig {
    private String accessKey;
    private String secreteKey;

    @Bean
    public SenlouApiClient senlouApiClient(){
        return new SenlouApiClient(accessKey,secreteKey);
    }
}
