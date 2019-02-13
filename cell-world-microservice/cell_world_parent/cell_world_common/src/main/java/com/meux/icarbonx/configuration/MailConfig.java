package com.meux.icarbonx.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 邮件
 */

@Configuration
@ConfigurationProperties(prefix = "command")
@Data
@NoArgsConstructor
public class MailConfig {
    private Integer cmd;
    /**
     * 系统邮件
     */
    private Integer sub_01;
    /**
     * 定向邮件
     */
    private Integer sub_02;
    /**
     * 公告
     */
    private Integer sub_03;
    /**
     * 发放道具
     */
    private Integer sub_04;
    private Integer sub_05;

    private Integer sub_06;
    private Integer sub_07;
    private Integer sub_08;
    private Integer sub_09;

    private String url;

}
