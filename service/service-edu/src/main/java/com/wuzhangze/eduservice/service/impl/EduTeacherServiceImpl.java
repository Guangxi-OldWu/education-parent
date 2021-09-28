package com.wuzhangze.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.eduservice.entity.EduCourse;
import com.wuzhangze.eduservice.entity.EduTeacher;
import com.wuzhangze.eduservice.entity.vo.CourseCoverVo;
import com.wuzhangze.eduservice.mapper.EduCourseMapper;
import com.wuzhangze.eduservice.mapper.EduTeacherMapper;
import com.wuzhangze.eduservice.service.EduCourseService;
import com.wuzhangze.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-08-18
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduCourseService courseService;

    /**
     * 查询热门讲师：根据讲师等级查询前四位讲师
     * @return
     */
    @Cacheable(value = "hotTeacher",key = "'selectIndexList'")
    @Override
    public List<EduTeacher> getHotTeacher() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("level").last("limit 4");
        List<EduTeacher> eduTeachers = baseMapper.selectList(wrapper);
        return eduTeachers;
    }
}
