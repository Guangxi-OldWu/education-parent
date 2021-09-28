package com.wuzhangze.ucenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author OldWu
 * @date 2021/9/5 17:40
 */
@Data
public class LoginVo {

    @ApiModelProperty(value = "手机号")
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^1[34578]\\d{9}$",message = "请输入正确的手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @Length(min = 6,max = 16,message = "密码为 6-16位")
    @NotEmpty
    private String password;
}
