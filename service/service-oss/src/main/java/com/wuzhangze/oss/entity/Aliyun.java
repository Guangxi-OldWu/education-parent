package com.wuzhangze.oss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author OldWu
 * @date 2021/8/22 16:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class Aliyun {
    private String endpoint;
    private String keyid;
    private String keysecret;
    private String bucketname;
}
