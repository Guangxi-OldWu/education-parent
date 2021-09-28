package com.wuzhangze.order.client;

import com.wuzhangze.servicebase.api.ApiManager;
import com.wuzhangze.servicebase.dto.CourseOrderInfo;
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
@FeignClient(value = "service-edu")
public interface CourseClient {
    @ApiOperation(value = "根据课程id获取课程基本信息")
    @GetMapping(ApiManager.V + "/eduservice/edu-course/getCourse/{courseId}")
    public CourseOrderInfo getCourse(@PathVariable("courseId")String courseId);
}
