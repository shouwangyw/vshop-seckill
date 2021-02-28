package com.veli.vshop.seckill.aop.lock;

import java.lang.annotation.*;

/**
 * @author yangwei
 * 自定义注解，实现aop Redis锁
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceRedisLock {
    String description() default "";
}
