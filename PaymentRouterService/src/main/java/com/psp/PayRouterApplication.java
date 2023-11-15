package com.psp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.psp.mapper")
public class PayRouterApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayRouterApplication.class, args);
    }
}
