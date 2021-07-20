package com.aurora.dds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.aurora.dds.mapper")
public class SpringBootDynamicDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDynamicDatasourceApplication.class, args);
    }

}
