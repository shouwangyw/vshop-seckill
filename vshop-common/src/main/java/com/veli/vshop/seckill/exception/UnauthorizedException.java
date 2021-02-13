package com.veli.vshop.seckill.exception;

import com.veli.vshop.seckill.response.RestResponseCode;

/**
 * @author yangwei
 * @date 2021-02-11 16:16
 */
public class UnauthorizedException extends CustomException {
    public UnauthorizedException() {
        super(RestResponseCode.TOKEN_OVERTIME);
    }
}
