package com.wuzhangze.eduservice.client.fallback;

import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author OldWu
 * @date 2021/9/1 21:32
 */
@Component
public class VodClientFallback implements VodClient
{
    @Override
    public R deleteAliyunVideo(String id) {
        return R.err().message("删除视频失败");
    }

    @Override
    public R deleteBatch(List<String> ids) {
        return R.err().message("批量删除视频失败");
    }
}
