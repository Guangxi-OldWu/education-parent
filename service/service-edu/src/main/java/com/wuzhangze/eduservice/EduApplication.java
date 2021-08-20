package com.wuzhangze.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author OldWu
 * @date 2021/8/18 16:23
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.wuzhangze"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
