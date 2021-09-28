package com.wuzhangze.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.api.ApiManager;
import com.wuzhangze.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author OldWu
 * @date 2021/8/30 17:36
 */
@RestController
@RequestMapping(ApiManager.V + "/eduvod/video")
public class VodController {

    @Resource
    private VodService vodService;

    @ApiOperation(value = "上传视频到 阿里云")
    @PostMapping("/uploadAliyunVideo")
    public R uploadAliyunVideo(@RequestPart MultipartFile file) {
        String videoId = vodService.uploadAliyunVideo(file);
        return R.ok().data("videoId",videoId);
    }

    @ApiOperation(value = "根据视频id 删除视频")
    @DeleteMapping("/deleteAliyunVideo/{id}")
    public R deleteAliyunVideo(@PathVariable("id") String id) {
        vodService.deleteAliyunVideo(id);
        return R.ok();
    }

    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestParam List<String> ids) {
        vodService.deleteBatchAliyunVideo(ids);
        return R.ok();
    }

    @ApiOperation(value = "根据视频id 获取视频播放凭证")
    @GetMapping("/getAliyunVideoPlayAuth/{id}")
    public R getAliyunVideoPlayAuth(@PathVariable String id) {
        String playAuth = vodService.getAliyunVideoPlayAuth(id);
        return R.ok().data("playAuth",playAuth);
    }
}
