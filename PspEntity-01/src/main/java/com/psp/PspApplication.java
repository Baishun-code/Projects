package com.psp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.psp.mapper")
@EnableEurekaClient
public class PspApplication {
    public static void main(String[] args) {
        SpringApplication.run(PspApplication.class, args);
    }
}
