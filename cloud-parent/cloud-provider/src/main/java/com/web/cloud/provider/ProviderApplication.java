package com.web.cloud.provider;

import com.web.cloud.provider.filter.AppCheckFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.web.cloud.common.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.web.cloud.common.manager",
        "com.web.cloud.provider.config",
        "com.web.cloud.provider.controller"})
public class ProviderApplication {

    @Bean
    public AppCheckFilter appCheckFilter(){
        return new AppCheckFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
