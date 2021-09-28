package com.wuzhangze.eduservice.controller;


import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.client.VodClient;
import com.wuzhangze.eduservice.entity.EduVideo;
import com.wuzhangze.eduservice.service.EduVideoService;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-08-24
 */
@RestController
@RequestMapping(ApiManager.V + "/eduservice/edu-video")
public class EduVideoController {

    @Resource
    private EduVideoService videoService;
    @Resource
    private VodClient vodClient;

    @ApiOperation(value = "添加小节信息")
    @PostMapping("/addVideoInfo")
    public R addVideoInfo(@RequestBody EduVideo video) {
        return videoService.save(video) ? R.ok() : R.err().message("添加小节失败");
    }

    @ApiOperation(value = "根据id查询小节信息")
    @GetMapping("/getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable("id") String id){
        return R.ok().obj(videoService.getById(id));
    }

    @ApiOperation(value = "更新小节信息")
    @PutMapping("/updateVideoInfo")
    public R updateVideoInfo(@RequestBody EduVideo video) {
        return videoService.updateById(video) ? R.ok() : R.err().message("更新小节信息失败");
    }

    @ApiOperation(value = "根据id删除小节信息")
    @DeleteMapping("/deleteVideoInfo/{id}")
    public R deleteVideoInfo(@PathVariable("id") String id) {
        EduVideo eduVideo = videoService.getById(id);
        if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
            R r = vodClient.deleteAliyunVideo(eduVideo.getVideoSourceId());
            if(r.getCode() != 20000) {
                return R.err().message(r.getMessage());
            }
        }
        return videoService.removeById(id) ? R.ok() : R.err().message("删除小节信息失败");
    }
}

