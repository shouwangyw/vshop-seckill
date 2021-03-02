package com.veli.vshop.seckill.aop.lock;

import com.veli.vshop.seckill.exception.CustomException;
import com.veli.vshop.seckill.util.RedissonLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

import static com.veli.vshop.seckill.domain.CommonConstants.SEC_KILL_GOODS_LOCK_PREFIX;

/**
 * @author yangwei
 */
@Slf4j
@Component
@Scope
@Aspect
@Order(1)
public class LockRedisAspect {
    /**
     * 定义锁对象
     */
    @Resource
    private HttpServletRequest request;

    /**
     * Service切入点
     */
    @Pointcut("@annotation(com.veli.vshop.seckill.aop.lock.ServiceRedisLock)")
    public void lockAspect() {

    }

    @Around("lockAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        // 获取id
        String uri = request.getRequestURI();
        String id = uri.substring(uri.lastIndexOf("/") - 1, uri.lastIndexOf("/"));
        // 初始化一个对象
        Object obj = null;
        // 加锁: 先获取一把锁
        String lockKey = SEC_KILL_GOODS_LOCK_PREFIX + id;
        boolean result = RedissonLockUtils.tryLock(lockKey, 3, 10, TimeUnit.SECONDS);

        try {
            if (result) {
                // 执行业务
                obj = joinPoint.proceed();
            }
        } catch (Throwable cause) {
            if (cause instanceof CustomException) {
                throw (CustomException) cause;
            } else {
                log.error(cause.getMessage());
            }
        } finally {
            // 业务执行结束后，释放锁
            if (result) {
                RedissonLockUtils.unlock(lockKey);
            }
        }
        return obj;
    }
}
