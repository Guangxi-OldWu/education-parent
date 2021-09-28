package com.wuzhangze.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author OldWu
 * @date 2021/9/10 21:15
 */
@MapperScan(basePackages = {"com.wuzhangze.order.mapper"})
@EnableFeignClients
@ComponentScan(basePackages = {"com.wuzhangze"})
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
