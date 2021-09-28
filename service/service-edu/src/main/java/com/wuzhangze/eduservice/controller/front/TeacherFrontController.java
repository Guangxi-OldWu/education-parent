package com.wuzhangze.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.commonutil.ResultCode;
import com.wuzhangze.eduservice.entity.EduTeacher;
import com.wuzhangze.eduservice.entity.vo.CourseCoverVo;
import com.wuzhangze.eduservice.service.EduCourseService;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wuzhangze.eduservice.service.EduTeacherService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author OldWu
 * @date 2021/9/7 20:02
 */
@RequestMapping(ApiManager.V + "/eduservice/teacher/front")
@RestController
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    @ApiOperation("根据分页获取讲师列表")
    @GetMapping("/page/{current}/{size}")
    public R pageListTeacher(@PathVariable("current") @ApiParam(name = "current",value = "当前页") Long current,
                             @PathVariable("size") @ApiParam(name = "size",value = "每一页数据量") Long size){
        Page<EduTeacher> teacherPage = teacherService.page(
                                        new Page<>(current,size),
                                        new QueryWrapper<EduTeacher>().orderByDesc("level"));

        Map map = new HashMap();
        map.put("items",teacherPage.getRecords());
        map.put("current",current);
        map.put("pages",teacherPage.getPages());
        map.put("size",teacherPage.getSize());
        map.put("total",teacherPage.getTotal());
        map.put("hasNext",teacherPage.hasNext());
        map.put("hasPrevious",teacherPage.hasPrevious());
        return R.ok().data(map);
    }

    @ApiOperation(value = "获取讲师详情和讲师上的课（已发布）")
    @GetMapping("/get/{id}")
    public R getTeacherById(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        List<CourseCoverVo> courseList = courseService.getCourseByTeacherId(teacher.getId());
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }

}
