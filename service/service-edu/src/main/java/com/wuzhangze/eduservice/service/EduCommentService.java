package com.wuzhangze.eduservice.service;

import com.wuzhangze.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-09
 */
public interface EduCommentService extends IService<EduComment> {

    /**
     * 根据id 删除评论，如果是父评论连同子评论一起删除
     * @param id
     */
    void removeCommentById(String id);
}
