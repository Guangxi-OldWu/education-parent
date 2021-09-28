package com.wuzhangze.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author OldWu
 * @date 2021/9/14 17:40
 */
@EnableTransactionManagement
@EnableScheduling
@MapperScan(basePackages = {"com.wuzhangze.statistics.mapper"})
@ComponentScan(basePackages = {"com.wuzhangze"})
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class,args);
    }
}
