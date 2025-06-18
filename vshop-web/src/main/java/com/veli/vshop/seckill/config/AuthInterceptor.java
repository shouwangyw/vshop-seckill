package com.veli.vshop.seckill.config;

import com.veli.vshop.seckill.component.RequestContext;
import com.veli.vshop.seckill.domain.BaseUser;
import com.veli.vshop.seckill.domain.CommonConstants;
import com.veli.vshop.seckill.exception.UnauthorizedException;
import com.veli.vshop.seckill.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangwei
 * @date 2021-02-11 09:35
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;
    @Resource
    private RequestContext requestContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(CommonConstants.AUTH_TOKEN);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(CommonConstants.AUTH_TOKEN);
        }
        if (StringUtils.isBlank(token)) {
            throw new UnauthorizedException();
        }
        BaseUser user = userService.queryUserByToken(token);
        if (user == null) {
            throw new UnauthorizedException();
        }
        return true;
    }
}
