package com.wuzhangze.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.entity.EduCourse;
import com.wuzhangze.eduservice.service.EduCourseService;
import com.wuzhangze.eduservice.service.EduTeacherService;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author OldWu
 * @date 2021/9/3 13:10
 */
@RequestMapping(ApiManager.V + "/eduservice/index")
@RestController
public class IndexController {

    @Resource
    private EduTeacherService teacherService;
    @Resource
    private EduCourseService courseService;


    @ApiOperation(value = "获取首页热门讲师和课程")
    @GetMapping("/")
    public R index() {
        return R.ok().data("teacher",teacherService.getHotTeacher()).data("course",courseService.getHotCourse());
    }


}
