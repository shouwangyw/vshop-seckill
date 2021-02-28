package com.veli.vshop.seckill.exception;

import com.veli.vshop.seckill.response.RestResponseCode;

/**
 * @author yangwei
 * @date 2021-02-11 16:16
 */
public class SeckillOrderException extends CustomException {
    public SeckillOrderException() {
        super(RestResponseCode.SEC_GOODS_STOCK_FAIL);
    }

    public SeckillOrderException(RestResponseCode code) {
        super(code);
    }
}
