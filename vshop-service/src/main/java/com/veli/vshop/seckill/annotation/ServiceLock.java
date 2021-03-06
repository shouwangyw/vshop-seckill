package com.veli.vshop.seckill.annotation;

import java.lang.annotation.*;

/**
 * @author yangwei
 * 自定义注解，实现aop锁
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLock {
    String description() default "";
}
