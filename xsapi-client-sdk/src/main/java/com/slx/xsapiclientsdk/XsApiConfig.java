package com.slx.xsapiclientsdk;

import com.slx.xsapiclientsdk.client.XsApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author slx
 */
@Data
@Configuration
@ComponentScan
@ConfigurationProperties("xsapi.client")
public class XsApiConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public XsApiClient xsApiClient() {
        return new XsApiClient(accessKey, secretKey);
    }
}
