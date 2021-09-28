package com.wuzhangze.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.entity.EduChapter;
import com.wuzhangze.eduservice.entity.EduVideo;
import com.wuzhangze.eduservice.mapper.EduChapterMapper;
import com.wuzhangze.eduservice.mapper.EduVideoMapper;
import com.wuzhangze.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-08-24
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    private EduVideoMapper videoMapper;

    @Override
    public List<EduChapter> getChapterVideo(List<EduChapter> chapters,List<EduVideo> videos) {

        for (int i=0; i<chapters.size(); i++) {
            EduChapter chapter = chapters.get(i);
            List<EduVideo> eduVideos = new ArrayList<>();
            for (int j=0; j<videos.size(); j++) {
                EduVideo video = videos.get(j);
                if(chapter.getId().equals(video.getChapterId())) {
                    eduVideos.add(video);
                    videos.remove(j--);
                }
            }
            chapter.setVideos(eduVideos);
        }
        return chapters;
    }

    @Override
    public R removeChapter(String chapterId) {
        Page<EduVideo> page = new Page<>(1,1);
        List<EduVideo> records = videoMapper.selectPage(page, new QueryWrapper<EduVideo>().eq("chapter_id", chapterId)).getRecords();
        if(records != null && records.size() > 0) {
            return R.err().message("章节下有小节信息，不能删除！");
        }
        return baseMapper.deleteById(chapterId) > 0 ? R.ok() : R.err().message("没有id为："+chapterId+"的章节，删除失败！");
    }


}
