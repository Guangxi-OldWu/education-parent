package com.wuzhangze.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhangze.eduservice.entity.EduChapter;
import com.wuzhangze.eduservice.entity.EduCourse;
import com.wuzhangze.eduservice.entity.EduCourseDescription;
import com.wuzhangze.eduservice.entity.EduVideo;
import com.wuzhangze.eduservice.entity.vo.*;
import com.wuzhangze.eduservice.mapper.EduCourseDescriptionMapper;
import com.wuzhangze.eduservice.mapper.EduCourseMapper;
import com.wuzhangze.eduservice.service.EduChapterService;
import com.wuzhangze.eduservice.service.EduCourseDescriptionService;
import com.wuzhangze.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuzhangze.eduservice.service.EduVideoService;
import com.wuzhangze.servicebase.exception.LzException;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-08-24
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource
    private EduCourseDescriptionService courseDescriptionService;
    @Resource
    private EduVideoService videoService;
    @Resource
    private EduChapterService chapterService;

    // 添加课程基本信息
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert <= 0) {
            throw new LzException(20001,"添加课程信息失败");
        }

        EduCourseDescription desc = new EduCourseDescription();
        desc.setId(eduCourse.getId());
        desc.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(desc);
        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(baseMapper.selectById(courseId),courseInfoVo);
        courseInfoVo.setDescription(courseDescriptionService.getById(courseInfoVo.getId()).getDescription());
        return courseInfoVo;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateCourseInfoById(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        baseMapper.updateById(eduCourse);

        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,description);
        courseDescriptionService.updateById(description);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean removeCourseById(String id) {
        int i = baseMapper.deleteById(id);
        courseDescriptionService.removeById(id);
        videoService.removeByCourseId(id);
        chapterService.remove(new QueryWrapper<EduChapter>().eq("course_id",id));
        return i > 0;
    }

    @Override
    public CoursePublishVo getCoursePublish(String id) {
        return baseMapper.selectCoursePublish(id);
    }

    /**
     * @return
     */
    @Cacheable(value = "hotCourse",key = "'selectIndexList'")
    @Override
    public List<EduCourse> getHotCourse() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count").last("limit 8");
        List<EduCourse> eduCourses = baseMapper.selectList(wrapper);
        return eduCourses;
    }

    @Override
    public List<CourseCoverVo> getCourseByTeacherId(String teacherId) {
        return baseMapper.selectCourseByTeacherId(teacherId);
    }

    /**
     * 根据条件查询课程列表（分页）
     * @param page
     * @param courseQuery
     * @return 分页需要的数据
     */
    @Override
    public Map<String, Object> getCoursePageByCondition(Page<EduCourse> page, CourseFrontQuery courseQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("status","Normal");
        if(!StringUtils.isEmpty(courseQuery.getOneSubjectId())) {
            wrapper.eq("subject_parent_id",courseQuery.getOneSubjectId());
        }
        if(!StringUtils.isEmpty(courseQuery.getTwoSubjectId())) {
            wrapper.eq("subject_id",courseQuery.getTwoSubjectId());
        }
        if(!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            wrapper.orderByDesc("price");
        }

        Page<EduCourse> coursePage = baseMapper.selectPage(page, wrapper);
        Map map = new HashMap();
        map.put("items",coursePage.getRecords());
        map.put("current",coursePage.getCurrent());
        map.put("pages",coursePage.getPages());
        map.put("size",coursePage.getSize());
        map.put("total",coursePage.getTotal());
        map.put("hasNext",coursePage.hasNext());
        map.put("hasPrevious",coursePage.hasPrevious());
        return map;
    }

    @Override
    public CourseFrontVo getCourseFrontVoInto(String courseId) {
        return baseMapper.selectCourseFrontVoInfo(courseId);
    }
}
