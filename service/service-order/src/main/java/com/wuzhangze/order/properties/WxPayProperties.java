package com.wuzhangze.order.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author OldWu
 * @date 2021/9/12 21:22
 */
@ConfigurationProperties(prefix = "wx.open")
@Component
@Data
public class WxPayProperties {
    @ApiModelProperty(value = "关联的公众号appid")
    private String appId;
    @ApiModelProperty(value = "商户号")
    private String partner;
    @ApiModelProperty(value = "商户key")
    private String partnerKey;
    @ApiModelProperty(value = "回调地址")
    private String notifyUrl;
}
