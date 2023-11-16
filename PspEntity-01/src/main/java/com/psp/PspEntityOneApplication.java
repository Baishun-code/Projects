package com.psp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.psp.mapper")
@EnableEurekaClient
public class PspEntityOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(PspEntityOneApplication.class, args);
    }
}
