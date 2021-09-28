package com.wuzhangze.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author OldWu
 * @date 2021/9/3 11:22
 */
@MapperScan(basePackages = {"com.wuzhangze.cms.mapper"})
@ComponentScan(basePackages = {"com.wuzhangze"})
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class CmsApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CmsApplication.class, args);
    }
}
