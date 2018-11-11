package com.fosmos;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableCaching
@MapperScan("com.fosmos.mapper")
public class FosmosApplication {

    public static void main(String[] args) {
        SpringApplication.run(FosmosApplication.class, args);
    }

}
