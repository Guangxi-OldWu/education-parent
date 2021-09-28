package com.wuzhangze.gateway;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author OldWu
 * @date 2021/9/17 19:44
 */
@RibbonClients(value = {
        @RibbonClient(name = "service-edu",configuration = {WeightedResponseTimeRule.class}),
})
@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
