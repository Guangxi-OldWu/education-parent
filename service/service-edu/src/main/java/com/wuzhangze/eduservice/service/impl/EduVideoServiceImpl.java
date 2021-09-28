package com.wuzhangze.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.eduservice.client.VodClient;
import com.wuzhangze.eduservice.entity.EduVideo;
import com.wuzhangze.eduservice.mapper.EduVideoMapper;
import com.wuzhangze.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-08-24
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Resource
    private VodClient vodClient;

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.select("video_source_id").eq("course_id",courseId);
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);
        // 在EduVideo集合中，取出videoSourceId 转成id集合
        List<String> ids = eduVideos.stream()
                .map(EduVideo::getVideoSourceId)
                .filter(source -> { return source!=null && !StringUtils.isEmpty(source); })
                .collect(Collectors.toList());

        vodClient.deleteBatch(ids);
        baseMapper.delete(new QueryWrapper<EduVideo>().eq("course_id",courseId));
    }

}
