package com.wuzhangze.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.eduservice.entity.EduComment;
import com.wuzhangze.eduservice.mapper.EduCommentMapper;
import com.wuzhangze.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-09
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    /**
     * 根据id 删除评论，如果该评论是父评论，连通子评论一起删除
     * @param id
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void removeCommentById(String id) {
        EduComment comment = baseMapper.selectById(id);
        if(comment != null) {
            if(comment.getParentId() == null) {
                baseMapper.delete(new QueryWrapper<EduComment>().eq("parent_id",id));
            }
        }
        baseMapper.deleteById(id);
    }
}
