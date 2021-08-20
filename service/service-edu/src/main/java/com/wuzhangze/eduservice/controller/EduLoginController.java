package com.wuzhangze.eduservice.controller;

import com.wuzhangze.commonutil.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * @author OldWu
 * @date 2021/8/20 16:21
 */
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {

    @ApiOperation(value = "后台登录")
    @PostMapping("/login")
    public R login(
            @ApiParam(name = "map",value = "接收用户名密码",example = "{username=xxx,password=xxx}")
            @RequestBody Map<String,String> map){
        return R.ok().data("token", UUID.randomUUID().toString());
    }

    @ApiOperation(value = "获取用户基本信息")
    @GetMapping("/info")
    public R info(String token){
        String[] roles = {"admin"};
        return R.ok().data("roles",roles).data("name","admin").data("avatar","https://www.baidu.com/img/flexible/logo/pc/result.png");
    }
}
