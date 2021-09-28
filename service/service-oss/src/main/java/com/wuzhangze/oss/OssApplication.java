package com.wuzhangze.oss;

import com.wuzhangze.oss.entity.Aliyun;
import com.wuzhangze.oss.util.ConstantPropertyUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author OldWu
 * @date 2021/8/22 16:01
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.wuzhangze")
public class OssApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(OssApplication.class, args);
    }
}
