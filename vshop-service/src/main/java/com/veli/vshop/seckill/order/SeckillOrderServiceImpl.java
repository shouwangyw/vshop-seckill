package com.veli.vshop.seckill.order;

import com.veli.vshop.seckill.aop.lock.ServiceLock;
import com.veli.vshop.seckill.aop.lock.ServiceRedisLock;
import com.veli.vshop.seckill.aop.lock.ServiceZkLock;
import com.veli.vshop.seckill.dao.entity.TbSeckillGoods;
import com.veli.vshop.seckill.dao.entity.TbSeckillOrder;
import com.veli.vshop.seckill.dao.mapper.TbSeckillGoodsMapper;
import com.veli.vshop.seckill.dao.mapper.TbSeckillOrderMapper;
import com.veli.vshop.seckill.exception.SeckillOrderException;
import com.veli.vshop.seckill.response.RestResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangwei
 * @date 2021-02-27 10:03
 */
@Slf4j
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {
    @Resource
    private TbSeckillGoodsMapper seckillGoodsMapper;
    @Resource
    private TbSeckillOrderMapper seckillOrderMapper;

    /**
     * 互斥锁，参数默认false:不公平锁
     */
    private Lock lock = new ReentrantLock(true);


    @Override
    public boolean generalKilledByLock(Long id, String userId) {
        boolean result = false;
        lock.lock();
        try {
            result = generalKilled(id, userId);
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @ServiceLock
    @Override
    public boolean generalKilled(Long id, String userId) {
        boolean result = false;
//        lock.lock();
//        try {
        // 1、从数据库查询商品数据，并进行校验
        TbSeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKey(id);
        validateSeckillGoods(seckillGoods);
        // 2、扣减库存
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        // 3、更新库存
        seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
        // 4、下单
        TbSeckillOrder seckillOrder = new TbSeckillOrder()
                .setSeckillId(id)
                .setUserId(userId)
                .setCreatedTime(System.currentTimeMillis())
                .setStatus(0)
                .setMoney(seckillGoods.getCostPrice());

        result = seckillOrderMapper.insertSelective(seckillOrder) >= 1;
//        } finally {
//            lock.unlock();
//        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean sqlLockKilled(Long id, String userId) {
        // 1、从数据库查询商品数据，并进行校验
        TbSeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKeyWithLock(id);
        validateSeckillGoods(seckillGoods);
        // 2、扣减库存
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        // 3、更新库存
        seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);

        return killOrder(seckillGoods, id, userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean versionLockKilled(Long id, String userId) {
        // 1、从数据库查询商品数据，并进行校验
        TbSeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKey(id);
        validateSeckillGoods(seckillGoods);
        // 2、扣减库存
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        // 3、更新库存：使用数据库乐观锁方法
        int result = seckillGoodsMapper.updateByPrimaryKeyWithVersion(id, seckillGoods.getVersion());

        return result >= 1 && killOrder(seckillGoods, id, userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @ServiceRedisLock
//    @ServiceZkLock
    @Override
    public boolean redisLockKilled(Long id, String userId) {
        // 1、从数据库查询商品数据，并进行校验
        TbSeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKey(id);
        validateSeckillGoods(seckillGoods);
        // 2、扣减库存
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        // 3、更新库存
        seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
        // 4、下单
        TbSeckillOrder seckillOrder = new TbSeckillOrder()
                .setSeckillId(id)
                .setUserId(userId)
                .setCreatedTime(System.currentTimeMillis())
                .setStatus(0)
                .setMoney(seckillGoods.getCostPrice());

        return seckillOrderMapper.insertSelective(seckillOrder) >= 1;
    }

    private boolean killOrder(TbSeckillGoods seckillGoods, Long id, String userId) {
        // 4、下单
        TbSeckillOrder seckillOrder = new TbSeckillOrder()
                .setSeckillId(id)
                .setUserId(userId)
                .setCreatedTime(System.currentTimeMillis())
                .setStatus(0)
                .setMoney(seckillGoods.getCostPrice());

        return seckillOrderMapper.insertSelective(seckillOrder) >= 1;
    }

    private void validateSeckillGoods(TbSeckillGoods seckillGoods) {
        if (seckillGoods == null) {
            throw new SeckillOrderException(RestResponseCode.SEC_GOODS_NOT_EXSISTS);
        }
        if (seckillGoods.getStartTime() > System.currentTimeMillis()) {
            throw new SeckillOrderException(RestResponseCode.SEC_ACTIVE_NOT_START);
        }
        if (seckillGoods.getEndTime() <= System.currentTimeMillis()) {
            throw new SeckillOrderException(RestResponseCode.SEC_ACTIVE_END);
        }
        if (seckillGoods.getStatus() != 1) {
            throw new SeckillOrderException(RestResponseCode.SEC_NOT_UP);
        }
        if (seckillGoods.getStockCount() <= 0) {
            throw new SeckillOrderException(RestResponseCode.SEC_GOODS_END);
        }
    }
}
