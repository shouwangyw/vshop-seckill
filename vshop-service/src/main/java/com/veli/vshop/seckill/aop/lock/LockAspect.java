package com.veli.vshop.seckill.aop.lock;

import com.veli.vshop.seckill.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangwei
 */
@Slf4j
@Component
@Scope
@Aspect
@Order(1)
public class LockAspect {
    /**
     * 定义锁对象
     */
    private static Lock LOCK = new ReentrantLock(true);
    /**
     * Service切入点
     */
    @Pointcut("@annotation(com.veli.vshop.seckill.aop.lock.ServiceLock)")
    public void lockAspect() {

    }

    @Around("lockAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        // 初始化一个对象
        Object obj = null;
        // 加锁
        LOCK.lock();
        try {
            // 执行业务
            obj = joinPoint.proceed();
        } catch (Throwable cause) {
            if (cause instanceof CustomException) {
                throw (CustomException) cause;
            } else {
                log.error(cause.getMessage());
            }
        } finally {
            // 业务执行结束后，释放锁
            LOCK.unlock();
        }
        return obj;
    }
}
