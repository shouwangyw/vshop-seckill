package com.veli.vshop.seckill.controller;

import com.veli.vshop.seckill.dao.entity.TbSeckillGoods;
import com.veli.vshop.seckill.domain.BasePageInfo;
import com.veli.vshop.seckill.order.SeckillGoodsService;
import com.veli.vshop.seckill.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yangwei
 */
@Api(tags = "秒杀商品模块")
@RestController
@RequestMapping("seckill")
public class SeckillGoodsController {
    @Resource
    private SeckillGoodsService seckillGoodsService;

    @ApiOperation("添加商品")
    @PostMapping("/goods/save")
    public RestResponse<Boolean> saveOne(@RequestBody TbSeckillGoods tbSeckillGoods) {
        return RestResponse.success(seckillGoodsService.saveOne(tbSeckillGoods));
    }

    @ApiOperation("分页查询商品列表")
    @GetMapping("/goods/list/{pageNum}/{pageSize}")
    public RestResponse<BasePageInfo<TbSeckillGoods>> listGoods(@PathVariable Integer pageNum,
                                                                @PathVariable Integer pageSize) {
        return RestResponse.success(seckillGoodsService.queryGoodsByPage(pageNum, pageSize));
    }

    @ApiOperation("获取商品详情信息")
    @GetMapping("/goods/detail/{id}")
    public RestResponse<TbSeckillGoods> queryDetails(@PathVariable Integer id) {
        return RestResponse.success(seckillGoodsService.queryGoodsDetailsByCache(id));
    }

    @ApiOperation("刷新Redis缓存：秒杀商品")
    @GetMapping("/goods/refresh/cache/{id}")
    public RestResponse<Boolean> refreshCache(@PathVariable Integer id) {
        return RestResponse.success(seckillGoodsService.refreshCache(id));
    }
}
