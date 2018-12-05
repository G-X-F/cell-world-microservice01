package com.meux.icarbonx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.meux.icarbonx"})
@ComponentScan("com.meux.icarbonx")
public class CellWorld80_Feign_APP {
    public static void main(String[] args) {
        SpringApplication.run(CellWorld80_Feign_APP.class);
    }
}
