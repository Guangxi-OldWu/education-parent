package com.wuzhangze.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author OldWu
 * @date 2021/8/22 16:36
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
