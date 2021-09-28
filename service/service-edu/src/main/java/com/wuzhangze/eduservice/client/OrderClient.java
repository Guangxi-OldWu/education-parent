package com.wuzhangze.eduservice.client;

import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author OldWu
 * @date 2021/9/13 21:33
 */
@Component
@FeignClient(value = "service-order")
public interface OrderClient {
    @ApiOperation(value = "根据会员id 和 课程id 查询该课程是否被会员购买。0未购买/1已购买")
    @GetMapping(ApiManager.V + "/eduorder/order/status/{memberId}/{courseId}")
    public boolean getOrderStatus(@PathVariable("memberId") String memberId,
                                  @PathVariable("courseId") String courseId);
}
