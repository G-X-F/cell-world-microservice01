package com.meux.icarbonx.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fileConfig")
@Data
@NoArgsConstructor
public class FileConfig {
    private String fileType;
    private String targetPath;
}
