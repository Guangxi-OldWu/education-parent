package com.wuzhangze.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhangze.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuzhangze.eduservice.entity.vo.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-08-24
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     */
    public String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id获取课程基本信息
     */
    public CourseInfoVo getCourseInfoById(String courseId);

    /**
     * 根据课程id 修改课程基本信息
     */
    public void updateCourseInfoById(CourseInfoVo courseInfoVo);

    /**
     * 获取课程基本信息、老师名、一级二级分类、简介
     * @param id
     * @return 多表的课程信息
     */
    CoursePublishVo getCoursePublish(String id);

    /**
     * 根据id 删除课程
     * @param id
     * @return
     */
    boolean removeCourseById(String id);

    /**
     * 获取热门课程
     * @return
     */
    List<EduCourse> getHotCourse();

    /**
     * 根据教师id 获取他所上的课程
     * @param teacherId
     * @return
     */
    List<CourseCoverVo> getCourseByTeacherId(String teacherId);

    /**
     * 根据条件查询课程列表（分页）
     * @param page
     * @param courseQuery
     * @return 分页需要的数据
     */
    Map<String,Object> getCoursePageByCondition(Page<EduCourse> page, CourseFrontQuery courseQuery);

    /**
     * 课程前台的详情vo类，有教师信息，一级二级课程信息，课程及课程详情信息
     * @param courseId
     * @return
     */
    CourseFrontVo getCourseFrontVoInto(String courseId);
}
