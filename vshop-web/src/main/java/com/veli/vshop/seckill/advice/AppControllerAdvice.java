package com.veli.vshop.seckill.advice;

import com.veli.vshop.seckill.exception.CustomException;
import com.veli.vshop.seckill.response.RestResponse;
import com.veli.vshop.seckill.response.RestResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangwei
 * @date 2021-02-07 22:00
 */
@Slf4j
@ControllerAdvice
public class AppControllerAdvice {
    @ExceptionHandler
    @ResponseBody
    public RestResponse processException(HttpServletRequest request, Exception e) {
        processLog(request, e);
        return processResponse(e);
    }

    private RestResponse processResponse(Exception e) {
        if (e instanceof CustomException) {
            return RestResponse.error(((CustomException) e).getCode(), ((CustomException) e).getMsg());
        }
        return RestResponse.error(RestResponseCode.UNKOWN_EXCEPTION, e.getMessage());
    }

    private void processLog(HttpServletRequest request, Exception e) {

    }
}
