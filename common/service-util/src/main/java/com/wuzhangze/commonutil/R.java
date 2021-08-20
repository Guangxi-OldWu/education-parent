package com.wuzhangze.commonutil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author OldWu
 * @date 2021/8/18 17:54
 */
@Data
public class R<T> {
    @ApiModelProperty("返回是否成功")
    private Boolean success;
    @ApiModelProperty("返回状态码")
    private Integer code;
    @ApiModelProperty("返回内容")
    private String message;
    @ApiModelProperty("返回数据")
    private Map<String,Object> data = new HashMap<>();

    private R(){}

    public static R ok(){
        R r = new R();
        r.code = ResultCode.OK.code;
        r.success = true;
        r.message = "成功";
        return r;
    }

    public static R err(){
        R r = new R();
        r.code = ResultCode.FAIL.code;
        r.success = false;
        r.message = "失败";
        return r;
    }

    public R obj(Object obj){
        this.data.put("item",obj);
        return this;
    }

    public R list(List<Object> objs){
        this.data.put("items",objs);
        return this;
    }

    public R data(ResultCode key,Object val){
        this.data.put(key.resultName,val);
        return this;
    }

    public R data(String key,Object val){
        this.data.put(key,val);
        return this;
    }

    public R data(Map<String,Object> map){
        this.data = map;
        return this;
    }

    public R message(String msg){
        this.message = msg;
        return this;
    }

    public R code(ResultCode resultCode){
        this.code = resultCode.code;
        return this;
    }

    public R success(Boolean b){
        this.success = b;
        return this;
    }

}
