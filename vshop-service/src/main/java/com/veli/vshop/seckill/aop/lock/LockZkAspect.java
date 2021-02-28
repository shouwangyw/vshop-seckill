package com.veli.vshop.seckill.aop.lock;

import com.veli.vshop.seckill.exception.CustomException;
import com.veli.vshop.seckill.util.ZkLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 */
@Slf4j
@Component
@Scope
@Aspect
@Order(1)
public class LockZkAspect {
    /**
     * Service切入点
     */
    @Pointcut("@annotation(com.veli.vshop.seckill.aop.lock.ServiceZkLock)")
    public void lockAspect() {

    }

    @Around("lockAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        // 初始化一个对象
        Object obj = null;
        // 加锁
        boolean result = ZkLockUtils.acquire(10, TimeUnit.SECONDS);
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
                ZkLockUtils.release();
            }
        }
        return obj;
    }
}
