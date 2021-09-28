package com.wuzhangze.vod.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author OldWu
 * @date 2021/8/31 12:30
 */
public interface VodService {
    /**
     * 上传视频
     * @param file
     * @return
     */
    String uploadAliyunVideo(MultipartFile file);

    void deleteAliyunVideo(String videoId);

    void deleteBatchAliyunVideo(List<String> ids);

    String getAliyunVideoPlayAuth(String id);

}
