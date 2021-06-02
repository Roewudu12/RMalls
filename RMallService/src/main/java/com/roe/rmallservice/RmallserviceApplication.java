package com.roe.rmallservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.roe.rmallservice.mapper")
public class RmallserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RmallserviceApplication.class, args);
    }

}
