package com.meux.icarbonx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserApplication8002_App {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication8002_App.class);
    }
}
