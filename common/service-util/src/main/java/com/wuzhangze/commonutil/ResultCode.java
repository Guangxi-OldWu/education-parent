package com.wuzhangze.commonutil;

/**
 * @author OldWu
 * @date 2021/8/18 17:46
 */
public enum ResultCode {
    OK(20000),
    FAIL(40000),
    NOT_LOGIN(50000),
    IS_PAY(25000),
    ITEMS("items"),
    ITEM("item"),
    TOTAL("total");

    public Integer code;
    public String resultName;

    private ResultCode(Integer code){
        this.code = code;
    }
    private ResultCode(String resultName) {this.resultName = resultName;}
}
