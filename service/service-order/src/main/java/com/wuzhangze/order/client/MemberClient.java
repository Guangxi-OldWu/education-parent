package com.wuzhangze.order.client;

import com.wuzhangze.servicebase.api.ApiManager;
import com.wuzhangze.servicebase.dto.MemberOrderInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author OldWu
 * @date 2021/9/11 21:38
 */
@Component
@FeignClient(value = "service-ucenter")
public interface MemberClient {
    @ApiOperation(value = "根据用户id返回用户信息")
    @GetMapping(ApiManager.V + "/ucenter/member/{id}")
    public MemberOrderInfo getMemberOrderInfo(@PathVariable("id") String id);
}
