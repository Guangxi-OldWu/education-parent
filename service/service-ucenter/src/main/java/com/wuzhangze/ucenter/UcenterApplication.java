package com.wuzhangze.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author OldWu
 * @date 2021/9/4 23:42
 */
@MapperScan("com.wuzhangze.ucenter.mapper")
@ComponentScan(basePackages = {"com.wuzhangze"})
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
