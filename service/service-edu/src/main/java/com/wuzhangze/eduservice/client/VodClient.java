package com.wuzhangze.eduservice.client;

import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.client.fallback.VodClientFallback;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author OldWu
 * @date 2021/9/1 15:54
 */
@FeignClient(value = "service-vod",fallback = VodClientFallback.class)
@Component
public interface VodClient {

    @DeleteMapping(ApiManager.V + "/eduvod/video/deleteAliyunVideo/{id}")
    public R deleteAliyunVideo(@PathVariable("id") String id);


    /**
     * 批量删除视频
     * @param ids
     * @return
     */
    @DeleteMapping(ApiManager.V + "/eduvod/video/deleteBatch")
    public R deleteBatch(@RequestParam List<String> ids);
}
