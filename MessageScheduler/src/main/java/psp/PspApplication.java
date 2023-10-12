package psp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.psp.mapper")
@EnableScheduling
@EnableEurekaClient
public class PspApplication {
    public static void main(String[] args) {
        SpringApplication.run(PspApplication.class, args);
    }
}
