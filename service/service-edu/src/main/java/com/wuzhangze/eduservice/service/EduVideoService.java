package com.wuzhangze.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-08-24
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);
}
