package com.veli.vshop.seckill.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.cache.Cache;
import com.veli.vshop.seckill.dao.entity.TbSeckillGoods;
import com.veli.vshop.seckill.dao.mapper.TbSeckillGoodsMapper;
import com.veli.vshop.seckill.domain.BasePageInfo;
import com.veli.vshop.seckill.redis.RedisService;
import com.veli.vshop.seckill.util.CommonUtils;
import com.veli.vshop.seckill.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.veli.vshop.seckill.domain.CommonConstants.SEC_KILL_GOODS_CACHE_PREFIX;
import static com.veli.vshop.seckill.domain.CommonConstants.SEC_KILL_GOODS_STOCK_CACHE_PREFIX;

/**
 * @author yangwei
 */
@Slf4j
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Resource
    private TbSeckillGoodsMapper seckillGoodsMapper;
    @Resource
    private Cache<String, Object> guavaCacche;
    @Resource
    private RedisService redisService;

    @Override
    public boolean saveOne(TbSeckillGoods tbSeckillGoods) {
        return seckillGoodsMapper.insertSelective(tbSeckillGoods) >= 1;
    }

    @Override
    public BasePageInfo<TbSeckillGoods> queryGoodsByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<TbSeckillGoods> pageInfo = PageInfo.of(seckillGoodsMapper.selectByExample(null));
        return new BasePageInfo<>(pageInfo);
    }

    @Override
    public TbSeckillGoods queryGoodsDetails(Integer id) {
        // 直接从数据库查询: 主键查询——CPU不耗时操作
        TbSeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKey(id);
        // 模拟程序耗时操作，如果方法是一个比较耗时的操作，性能优化非常有必要的！！
        TimeUtils.sleepSec(1);
        log.info("==>> 模拟耗时操作，睡眠1s时间！对象占用jvm堆内存大小: {}", CommonUtils.size(seckillGoods));
        return seckillGoods;
    }

    @Override
    public TbSeckillGoods queryGoodsDetailsByCache(Integer id) {
        String cacheKey = SEC_KILL_GOODS_CACHE_PREFIX + id;
        // 1、先从JVM堆内存中读取数据，使用 guava 缓存
        TbSeckillGoods seckillGoods = (TbSeckillGoods) guavaCacche.getIfPresent(cacheKey);
        if (seckillGoods != null) {
            return seckillGoods;
        }
        // 2、如果JVM堆内存中不存在，则从分布式缓存(redis)中查询
        seckillGoods = redisService.getObjValue(cacheKey);
        if (seckillGoods != null) {
            // 添加进guava缓存
            guavaCacche.put(cacheKey, seckillGoods);
            return seckillGoods;
        }
        // 3、如果分布式缓存(redis)中还没有，则从数据库查询
        seckillGoods = seckillGoodsMapper.selectByPrimaryKey(id);
        if (seckillGoods != null && seckillGoods.getStatus() == 1) {
            // 添加进分布式缓存(redis)中
            redisService.setObjValue(cacheKey, seckillGoods, 30, TimeUnit.MINUTES);
        }
        return seckillGoods;
    }

    @Override
    public boolean refreshCache(Integer id) {
        redisService.removeAll(SEC_KILL_GOODS_CACHE_PREFIX + id, SEC_KILL_GOODS_STOCK_CACHE_PREFIX + id);
        if (id == null) {
            return false;
        }
        TbSeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKey(id);
        if (seckillGoods == null) {
            return false;
        }
        redisService.setObjValue(SEC_KILL_GOODS_CACHE_PREFIX + id, seckillGoods, 30, TimeUnit.MINUTES);
        redisService.setIntValue(SEC_KILL_GOODS_STOCK_CACHE_PREFIX + id, seckillGoods.getStockCount());
        return true;
    }
}
