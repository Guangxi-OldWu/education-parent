package com.wuzhangze.msm.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author OldWu
 * @date 2021/9/4 21:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "sms-config")
public class SmsProperties {
    private String secretId;
    private String secretKey;
    private String sdkAppId;
    private String signName;
    private String templateId;
}
