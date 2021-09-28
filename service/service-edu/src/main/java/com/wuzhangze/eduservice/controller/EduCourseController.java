package com.wuzhangze.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.entity.EduCourse;
import com.wuzhangze.eduservice.entity.vo.CourseInfoVo;
import com.wuzhangze.eduservice.entity.vo.CoursePublishVo;
import com.wuzhangze.eduservice.service.EduCourseService;
import com.wuzhangze.servicebase.api.ApiManager;
import com.wuzhangze.servicebase.dto.CourseOrderInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-08-24
 */
// TODO 修改课程基本信息，修改课程大纲，并更新缓存
@RestController
@RequestMapping(ApiManager.V + "/eduservice/edu-course")
public class EduCourseController {

    @Resource
    private EduCourseService courseService;

    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@ApiParam(value = "课程基本信息VO类")
                           @RequestBody CourseInfoVo courseInfo) {
        return R.ok().data("id",courseService.saveCourseInfo(courseInfo));
    }

    @ApiOperation(value = "根据课程id获取课程基本信息(包括简介)")
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId")String courseId) {
        return R.ok().obj(courseService.getCourseInfoById(courseId));
    }

    @ApiOperation(value = "根据课程id获取课程基本信息")
    @GetMapping("/getCourse/{courseId}")
    public CourseOrderInfo getCourse(@PathVariable("courseId")String courseId) {
        EduCourse course = courseService.getById(courseId);
        CourseOrderInfo courseOrderInfo = new CourseOrderInfo();
        BeanUtils.copyProperties(course,courseOrderInfo);
        return courseOrderInfo;
    }

    @ApiOperation(value = "根据课程id 修改课程基本信息")
    @PutMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfoById(courseInfoVo);
        return R.ok();
    }

    @ApiOperation(value = "查看课程的基本信息，一级二级分类，老师名，简介")
    @GetMapping("/getCoursePublish/{id}")
    public R getCoursePublish(@PathVariable("id")String id) {
        CoursePublishVo coursePublishInfo = courseService.getCoursePublish(id);
        return R.ok().obj(coursePublishInfo);
    }

    @ApiOperation(value = "发布课程")
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable("id") String id) {
        boolean b = courseService.update(new UpdateWrapper<EduCourse>().eq("id", id).set("status", "Normal"));
        return b ? R.ok() : R.err().message("课程发布失败");
    }

    @ApiOperation(value = "查询所有课程")
    @GetMapping("/findAll")
    public R findAll() {
        return R.ok().list(courseService.list());
    }

    @ApiOperation(value = "分页获取课程")
    @GetMapping("/getCourse/{current}/{size}")
    public R getCourseByLimit(@PathVariable("current")Integer current,
                              @PathVariable("size")Integer size) {
        Page<EduCourse> page = new Page<>(current,size);
        List<EduCourse> courses = courseService.page(page).getRecords();
        return R.ok().list(courses).data("total",page.getTotal());
    }

    @ApiOperation(value = "根据条件获取分页课程")
    @PostMapping("/getCourseByCondition/{current}/{size}")
    public R getCourseByCondition(@PathVariable("current")Integer current,
                                  @PathVariable("size")Integer size,
                                  @RequestBody EduCourse course) {
        Page<EduCourse> page = new Page<>(current,size);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(course != null) {
            if(!StringUtils.isEmpty(course.getTitle())) {
                wrapper.like("title",course.getTitle());
            }
            if(!StringUtils.isEmpty(course.getStatus())) {
                wrapper.eq("status",course.getStatus());
            }
        }
        List<EduCourse> courses = courseService.page(page,wrapper).getRecords();
        return R.ok().list(courses).data("total",page.getTotal());
    }

    @CacheEvict(value = "hotCourse")
    @ApiOperation("根据id删除课程")
    @DeleteMapping("/removeCourse/{id}")
    public R removeCourse(@PathVariable("id")String id) {
        return courseService.removeCourseById(id) ? R.ok() : R.err().message("删除课程失败");
    }


}

