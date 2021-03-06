package com.veli.vshop.seckill.controller;

import com.veli.vshop.seckill.domain.BaseUser;
import com.veli.vshop.seckill.order.SeckillOrderService;
import com.veli.vshop.seckill.response.RestResponse;
import com.veli.vshop.seckill.response.RestResponseCode;
import com.veli.vshop.seckill.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yangwei
 * @date 2021-02-27 09:57
 */
@Api(tags = "秒杀下单模块")
@RestController
@RequestMapping("seckill")
public class SeckillOrderController {
    @Resource
    private UserService userService;
    @Resource
    private SeckillOrderService seckillOrderService;

    @ApiOperation("① 普通下单操作")
    @GetMapping("/order/{id}/{token}")
    public RestResponse<Boolean> generalKilled(@PathVariable Long id,
                                               @PathVariable String token) {
        BaseUser baseUser = userService.queryUserByToken(token);
        if (baseUser == null) {
            return RestResponse.error(RestResponseCode.TOKEN_OVERTIME);
        }
        return RestResponse.success(seckillOrderService.generalKilled(id, baseUser.getGuid()));
//        return RestResponse.success(seckillOrderService.generalKilledByLock(id, baseUser.getGuid()));
    }

    @ApiOperation("② 数据库悲观锁下单，在分布式模式下控制库存")
    @GetMapping("/order/sql/{id}/{token}")
    public RestResponse<Boolean> sqlLockKilled(@PathVariable Long id,
                                               @PathVariable String token) {
        BaseUser baseUser = userService.queryUserByToken(token);
        if (baseUser == null) {
            return RestResponse.error(RestResponseCode.TOKEN_OVERTIME);
        }
        return RestResponse.success(seckillOrderService.sqlLockKilled(id, baseUser.getGuid()));
    }

    @ApiOperation("③ 数据库乐观锁下单，在分布式模式下控制库存")
    @GetMapping("/order/version/{id}/{token}")
    public RestResponse<Boolean> versionLockKilled(@PathVariable Long id,
                                                   @PathVariable String token) {
        BaseUser baseUser = userService.queryUserByToken(token);
        if (baseUser == null) {
            return RestResponse.error(RestResponseCode.TOKEN_OVERTIME);
        }
        return RestResponse.success(seckillOrderService.versionLockKilled(id, baseUser.getGuid()));
    }

    @ApiOperation("④ Redis分布式锁下单，在分布式模式下控制库存")
    @GetMapping("/order/redis/{id}/{token}")
    public RestResponse<Boolean> redisLockKilled(@PathVariable Long id,
                                                 @PathVariable String token) {
        BaseUser baseUser = userService.queryUserByToken(token);
        if (baseUser == null) {
            return RestResponse.error(RestResponseCode.TOKEN_OVERTIME);
        }
        return RestResponse.success(seckillOrderService.redisLockKilled(id, baseUser.getGuid()));
    }

    @ApiOperation("⑤ 利用Redis原子操作，实现库存控制和缓存优化")
    @GetMapping("/order/cache/{id}/{token}")
    public RestResponse<Boolean> redisCacheKilled(@PathVariable Long id,
                                                  @PathVariable String token) {
        BaseUser baseUser = userService.queryUserByToken(token);
        if (baseUser == null) {
            return RestResponse.error(RestResponseCode.TOKEN_OVERTIME);
        }
        return RestResponse.success(seckillOrderService.redisCacheKilled(id, baseUser.getGuid()));
    }

    @ApiOperation("⑥ 使用MQ异步下单，实现库存控制和缓存优化")
    @GetMapping("/order/mq/{id}/{token}")
    public RestResponse<Boolean> mqKilled(@PathVariable Long id,
                                          @PathVariable String token) {
        BaseUser baseUser = userService.queryUserByToken(token);
        if (baseUser == null) {
            return RestResponse.error(RestResponseCode.TOKEN_OVERTIME);
        }
        return RestResponse.success(seckillOrderService.mqKilled(id, baseUser.getGuid()));
    }
}
