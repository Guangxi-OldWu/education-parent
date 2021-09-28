package com.wuzhangze.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.entity.EduChapter;
import com.wuzhangze.eduservice.entity.EduVideo;
import com.wuzhangze.eduservice.service.EduChapterService;
import com.wuzhangze.eduservice.service.EduVideoService;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-08-24
 */
@RestController
@RequestMapping(ApiManager.V + "/eduservice/edu-chapter")
public class EduChapterController {

    @Resource
    EduChapterService chapterService;
    @Resource
    EduVideoService videoService;

    @ApiOperation(value = "获取章节和小节的信息")
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@ApiParam(value = "课程的id")
                             @PathVariable("courseId") String courseId) {
        List<EduChapter> chapterVideo = chapterService.getChapterVideo(
                chapterService.list(new QueryWrapper<EduChapter>().eq("course_id", courseId).orderByAsc("sort")),
                videoService.list(new QueryWrapper<EduVideo>().eq("course_id", courseId).orderByAsc("sort")));

        return R.ok().list(chapterVideo);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter chapter) {
        return chapterService.save(chapter) ? R.ok().obj(chapter) : R.err();
    }

    @ApiOperation(value = "根据章节id 查询章节信息")
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable("chapterId")String chapterId) {
        return R.ok().obj(chapterService.getById(chapterId));
    }

    @ApiOperation(value = "修改章节信息")
    @PutMapping("/updateChapterInfo")
    public R updateChapterInfo(@RequestBody EduChapter chapter) {
        return chapterService.updateById(chapter) ? R.ok() : R.err();
    }

    @ApiOperation(value = "删除章节信息")
    @DeleteMapping("/delete/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId")String chapterId) {
        return chapterService.removeChapter(chapterId);
    }
}

