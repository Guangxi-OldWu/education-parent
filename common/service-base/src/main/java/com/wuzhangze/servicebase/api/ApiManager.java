package com.wuzhangze.servicebase.api;

/**
 * @author OldWu
 * @date 2021/8/22 22:22
 */
public class ApiManager {

    /**
     * Version
     */
    public static final String V = "/api/v1";
    public static final String EDU_SERVICE = "/eduservice";

    /**
     * Teacher && User && Subject
     */
    public static final String TEACHER = V + EDU_SERVICE + "/edu-teacher";
    public static final String USER = V + EDU_SERVICE + "/user";
    public static final String SUBJECT = V + EDU_SERVICE + "/subject";

    /**
     * OSS
     */
    public static final String EDU_OSS = "/eduoss";
    public static final String FILE_OSS = V + EDU_OSS + "/fileoss";
}
