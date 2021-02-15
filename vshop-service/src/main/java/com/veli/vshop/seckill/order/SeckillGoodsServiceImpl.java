package com.veli.vshop.seckill.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.veli.vshop.seckill.dao.entity.TbSeckillGoods;
import com.veli.vshop.seckill.dao.mapper.TbSeckillGoodsMapper;
import com.veli.vshop.seckill.domain.BasePageInfo;
import com.veli.vshop.seckill.util.CommonUtils;
import com.veli.vshop.seckill.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yangwei
 * @date 2021-02-13 20:16
 */
@Slf4j
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Resource
    private TbSeckillGoodsMapper seckillGoodsMapper;

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
//        // 模拟程序耗时操作，如果方法是一个比较耗时的操作，性能优化非常有必要的！！
//        TimeUtils.sleepSec(1);
        log.info("==>> 模拟耗时操作，睡眠1s时间！对象占用jvm堆内存大小: {}", CommonUtils.size(seckillGoods));
        return seckillGoods;
    }
}
