package com.wuzhangze.eduservice.client;

import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author OldWu
 * @date 2021/9/9 22:21
 */
@Component
@FeignClient("service-ucenter")
public interface MemberClient {
    @ApiOperation(value = "根据 token 获取用户信息")
    @GetMapping(ApiManager.V + "/ucenter/member/get/{token}")
    public R getUserInfO(@PathVariable String token);
}
