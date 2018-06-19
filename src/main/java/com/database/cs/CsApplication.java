package com.database.cs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.database.cs.dao")
public class CsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsApplication.class, args);
    }
}
