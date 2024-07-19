package com.team404x.greenplate.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortoneConfig {
    @Value("[impkey]")
    private String impKey;
    @Value("[impSecret]")
    private String impSecret;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(impKey, impSecret);
    }
}
