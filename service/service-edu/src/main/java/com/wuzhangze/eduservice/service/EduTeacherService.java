package com.wuzhangze.eduservice.service;

import com.wuzhangze.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-08-18
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 获取热门讲师
     * @return
     */
    List<EduTeacher> getHotTeacher();

}
