package com.wuzhangze.oss.util;

import com.wuzhangze.oss.entity.Aliyun;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author OldWu
 * @date 2021/8/22 16:27
 */
@Component
public class ConstantPropertyUtils {
    @Resource
    private Aliyun aliyun;

    public static String EDNPOINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;
    @PostConstruct
    public void init(){
        EDNPOINT = aliyun.getEndpoint();
        KEY_ID = aliyun.getKeyid();
        KEY_SECRET = aliyun.getKeysecret();
        BUCKET_NAME = aliyun.getBucketname();
    }
}
