package com.wuzhangze.eduservice.mapper;

import com.wuzhangze.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuzhangze.eduservice.entity.vo.CourseCoverVo;
import com.wuzhangze.eduservice.entity.vo.CourseFrontVo;
import com.wuzhangze.eduservice.entity.vo.CoursePublishVo;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author OldWu
 * @since 2021-08-24
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * @param courseId
     * @return 课程基本信息、简介、一级二级分类、讲师
     */
    CoursePublishVo selectCoursePublish(String courseId);

    /**
     * 根据讲师id 查询该讲师上的课程（已发布课程）
     * @param teacherId
     * @return
     */
    List<CourseCoverVo> selectCourseByTeacherId(String teacherId);

    /**
     * 课程前台的详情vo类，有教师信息，一级二级课程信息，课程及课程详情信息
     * @param courseId
     * @return
     */
    CourseFrontVo selectCourseFrontVoInfo(String courseId);
}
