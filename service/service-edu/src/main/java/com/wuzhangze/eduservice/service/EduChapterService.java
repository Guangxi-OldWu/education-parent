package com.wuzhangze.eduservice.service;

import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuzhangze.eduservice.entity.EduVideo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-08-24
 */
public interface EduChapterService extends IService<EduChapter> {
    // 根据课程id 获取章节和小节的信息
    public List<EduChapter> getChapterVideo(List<EduChapter> chapters, List<EduVideo> videos);

    // 删除章节内容，里面如果有小节不能删除
    public R removeChapter(String chapterId);
}
