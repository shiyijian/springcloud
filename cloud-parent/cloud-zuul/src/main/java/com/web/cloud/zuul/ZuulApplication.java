package com.web.cloud.zuul;

import com.web.cloud.zuul.filter.HeadFilter;
import com.web.cloud.zuul.filter.RequestParamsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableCircuitBreaker
@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {

    @Bean
    public RequestParamsFilter requestParamsFilter(){
        return new RequestParamsFilter();
    }

    @Bean
    public HeadFilter headFilter(){
        return new HeadFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
