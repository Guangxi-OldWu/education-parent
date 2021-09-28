package com.wuzhangze.ucenter.test;

import com.google.gson.Gson;
import com.wuzhangze.ucenter.utils.OkHttpUtils;
import okhttp3.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author OldWu
 * @date 2021/9/6 20:19
 */
@SpringBootTest
public class OkHttpTest {

    public static void main(String[] args) {
        String url = "http://localhost:8001/api/v1/eduservice/edu-teacher/findAllx";
        OkHttpUtils.get(url,((call, response) -> {
            System.out.println(response.body().string());
        }));
    }

}
