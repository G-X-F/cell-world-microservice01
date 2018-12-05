package com.meux.icarbonx.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "command")
@Data
@NoArgsConstructor
public class CommandConfig {
    private Integer cmd;

    private Integer addItem;

    private Integer setLevel;

    private String url;
}
