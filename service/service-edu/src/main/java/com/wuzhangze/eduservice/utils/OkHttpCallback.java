package com.wuzhangze.eduservice.utils;

import com.wuzhangze.servicebase.exception.LzException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author OldWu
 * @date 2021/9/6 21:23
 */
public interface OkHttpCallback extends Callback {
    @Override
    default void onFailure(Call call, IOException e){
        throw new LzException(20001,"请求失败：" + e);
    }

    @Override
    void onResponse(Call call, Response response) throws IOException;
}
