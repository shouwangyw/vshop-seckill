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
    SEC_GOODS_NOT_EXSISTS(2002, "商品不存在"),
    SEC_ACTIVE_NOT_START(2003, "活动未开始"),
    SEC_ACTIVE_END(2004, "活动已结束"),
    SEC_NOT_UP(2005, "商品未审核"),
    SEC_GOODS_END(2006, "商品已售罄"),
    SEC_GOODS_STOCK_FAIL(2007, "下单失败"),
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