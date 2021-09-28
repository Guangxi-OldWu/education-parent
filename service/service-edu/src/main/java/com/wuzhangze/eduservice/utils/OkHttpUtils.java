package com.wuzhangze.eduservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author OldWu
 * @date 2021/9/6 20:52
 */
@Slf4j
public class OkHttpUtils {
    private static OkHttpClient client;
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 10000;
    public static final String POST = "post";
    public static final String PUT = "put";
    public static final String DELETE = "delete";

    private static OkHttpClient getInstance() {
        if(client == null) {
            synchronized (OkHttpClient.class) {
                if(client == null) {
                    client = new OkHttpClient.Builder()
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
                            // 最多允许20个空闲连接，五分钟不活动后被逐出
                            .connectionPool(new ConnectionPool(20,5,TimeUnit.MINUTES))
                            .build();
                }
            }
        }
        return client;
    }

    public static Response post(String url,Map<String,Object> map) {
        return request(POST,url,map);
    }

    public static void post(String url,Map<String,Object> map,OkHttpCallback callback) {
        request(POST,url,map,callback);
    }

    public static String postBody(String url,Map<String,Object> map) {
        Response res = OkHttpUtils.post(url,map);
        try {
            return res.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get(String url) {
        Request request = new Request.Builder().url(url).get().build();
        try {
            Response response = getInstance().newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void get(String url, OkHttpCallback callback) {
        Request request = new Request.Builder().url(url).get().build();
        getInstance().newCall(request).enqueue(callback);
    }

    public static void request(String method, String url, Map<String,Object> map,OkHttpCallback callback) {
        Request.Builder builder = new Request.Builder().url(url);
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = null;
        try {
            if(map != null) {
                jsonStr = mapper.writeValueAsString(map);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), StringUtils.isEmpty(jsonStr) ? "" : jsonStr);
        switch (method) {
            case POST: {
                builder.post(body);
            }break;
            case PUT: {
                builder.put(body);
            }break;
            case DELETE: {
                builder.delete(body);
            }break;
        }
        getInstance().newCall(builder.build()).enqueue(callback);
    }

    public static Response request(String method, String url, Map<String,Object> map) {
        Request.Builder builder = new Request.Builder().url(url);
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = null;
        try {
            if(map != null) {
                jsonStr = mapper.writeValueAsString(map);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), StringUtils.isEmpty(jsonStr) ? "" : jsonStr);
        switch (method) {
            case POST: {
                builder.post(body);
            }break;
            case PUT: {
                builder.put(body);
            }break;
            case DELETE: {
                builder.delete(body);
            }break;
        }
        try {
            Response response = getInstance().newCall(builder.build()).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
