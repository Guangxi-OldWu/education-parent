package com.wuzhangze.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wuzhangze.oss.service.OssService;
import com.wuzhangze.oss.util.ConstantPropertyUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @author OldWu
 * @date 2021/8/22 16:36
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertyUtils.EDNPOINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertyUtils.KEY_ID;
        String accessKeySecret = ConstantPropertyUtils.KEY_SECRET;
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."))+ UUID.randomUUID().toString();
            String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            // 1.Bucket名  2.oss文件路径和文件名
            ossClient.putObject(ConstantPropertyUtils.BUCKET_NAME, "edu/avatar/"+filename+fileSuffix, inputStream);
            return "https://"+ConstantPropertyUtils.BUCKET_NAME+"."+endpoint+"/edu/avatar/"+filename+fileSuffix;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }
}
