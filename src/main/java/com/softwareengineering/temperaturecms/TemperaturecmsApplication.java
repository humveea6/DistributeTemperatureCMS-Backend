package com.softwareengineering.temperaturecms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.softwareengineering.temperaturecms.dao")
public class TemperaturecmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemperaturecmsApplication.class, args);
    }

}
