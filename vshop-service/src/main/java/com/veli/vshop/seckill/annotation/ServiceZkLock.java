package com.veli.vshop.seckill.annotation;

import java.lang.annotation.*;

/**
 * @author yangwei
 * 自定义注解，实现aop zk锁
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceZkLock {
    String description() default "";
}
