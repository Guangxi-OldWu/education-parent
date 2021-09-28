package com.wuzhangze.statistics.client;

import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author OldWu
 * @date 2021/9/14 18:36
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @ApiOperation(value = "查询某一天的注册认数")
    @GetMapping(ApiManager.V + "/ucenter/member/count/{day}")
    public R countRegistry(@PathVariable("day") String day) ;
}
