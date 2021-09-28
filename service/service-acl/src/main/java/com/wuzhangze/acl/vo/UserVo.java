package com.wuzhangze.acl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author OldWu
 * @date 2021/9/19 22:07
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVo {
    private String id;
    @NotEmpty(message = "username不能为空")
    private String username;
    @NotEmpty(message = "password不能为空")
    private String password;
    @NotEmpty(message = "nickname不能为空")
    private String nickName;
}
