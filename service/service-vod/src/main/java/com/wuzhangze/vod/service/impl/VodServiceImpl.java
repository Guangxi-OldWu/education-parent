package com.wuzhangze.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wuzhangze.servicebase.exception.LzException;
import com.wuzhangze.vod.service.VodService;
import com.wuzhangze.vod.util.InitVodClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author OldWu
 * @date 2021/8/30 17:37
 */
@Service
public class VodServiceImpl implements VodService {

    @Value("${aliyun.vod.file.keyId}")
    private String accessKeyId;
    @Value("${aliyun.vod.file.keySecret}")
    private String accessKeySecret;


    @Override
    public String uploadAliyunVideo(MultipartFile file) {
        String title = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
        String fileName = file.getOriginalFilename();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        String videoId = null;
        System.out.print("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            videoId = response.getVideoId();
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            videoId = response.getVideoId();
//            System.out.print("ErrorCode=" + response.getCode() + "\n");
//            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        return videoId;
    }

    @Override
    public void deleteBatchAliyunVideo(List<String> ids) {
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(String.join(",",ids));
        DefaultAcsClient client = null;
        try {
            client = InitVodClient.initVodClient(accessKeyId, accessKeySecret);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new LzException(20001,"删除视频失败");
        }
    }

    @Override
    public String getAliyunVideoPlayAuth(String id) {
        DefaultAcsClient client = null;
        GetVideoPlayAuthResponse response = null;
        try {
            client = InitVodClient.initVodClient(accessKeyId, accessKeySecret);
            response = new GetVideoPlayAuthResponse();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new LzException(20001,"获取视频播放凭证失败");
        }
        //播放凭证
        return response.getPlayAuth();
    }

    @Override
    public void deleteAliyunVideo(String videoId) {
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videoId);
        DefaultAcsClient client = null;
        try {
            client = InitVodClient.initVodClient(accessKeyId, accessKeySecret);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new LzException(20001,"删除视频失败");
        }
    }

}
