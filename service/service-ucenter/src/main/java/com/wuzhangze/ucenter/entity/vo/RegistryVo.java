package com.wuzhangze.ucenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author OldWu
 * @date 2021/9/5 0:22
 */
@Data
public class RegistryVo {
    @ApiModelProperty(value = "id")
    private String id;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1([34578])\\d{9}$", message = "手机号格式错误")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @NotNull(message = "密码不能为空")
    @Length(min = 6,max = 16,message = "密码为 6-16位")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotNull(message = "昵称不能为空")
    @Length(min = 2,max = 10,message = "昵称长度为 2-10")
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @NotNull(message = "验证码不能为空")
    @Length(min = 6,max = 6,message = "请输入6位验证码")
    @ApiModelProperty(value = "验证码")
    private String code;
}
