package com.wuzhangze.ucenter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author OldWu
 * @date 2021/9/5 21:44
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx.open")
public class WeixinProperties {
    private String appId;
    private String appSecret;
    private String redirectUri;
}
