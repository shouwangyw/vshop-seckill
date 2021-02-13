package com.veli.vshop.seckill.response;

/**
 * @author yangwei
 */
public enum RestResponseCode {
    /**
     * 响应Code
     */
    /** 成功 **/
    SUCCESS(0, "Success"),
    /** 自定义错误 **/
    CUSTOM_ERROR(-100, "cms custom error"),
    /**
     * token过期
     */
    TOKEN_OVERTIME(201, "Token is overtime!"),
    /** 参数错误 **/
    PARAMS_EXCEPTION(-200, "Incoming parameter error"),
    /** 未知错误 **/
    UNKOWN_EXCEPTION(-900, "Server exception"),
    /** 其它错误 **/
    OTHER_ERROR(-1000, "other error"),

    /** 秒杀商品相关 **/

    ;

    private int code;
    private String msg;

    RestResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}