package com.wuzhangze.oss.controller;

import com.wuzhangze.commonutil.R;
import com.wuzhangze.oss.service.OssService;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author OldWu
 * @date 2021/8/22 16:34
 */
@RestController
@RequestMapping(ApiManager.FILE_OSS)
public class OssController {

    @Resource
    private OssService ossService;

    @ApiOperation(value = "文件上传")
    @PostMapping("/avatar")
    public R uploadOssFile(@ApiParam(name = "file",value = "文件对象") @RequestPart("file") MultipartFile file) {
        String avatarUrl = ossService.uploadFileAvatar(file);
        return R.ok().data("url",avatarUrl);
    }
}
