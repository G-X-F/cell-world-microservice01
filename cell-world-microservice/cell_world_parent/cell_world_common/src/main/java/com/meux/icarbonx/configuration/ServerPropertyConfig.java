package com.meux.icarbonx.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "propertyConfig")
@Data
@NoArgsConstructor
public class ServerPropertyConfig {
    private String type;
    private Map<Integer,String> wordMap;
    private Integer cmd;
    private Integer sub;
}
