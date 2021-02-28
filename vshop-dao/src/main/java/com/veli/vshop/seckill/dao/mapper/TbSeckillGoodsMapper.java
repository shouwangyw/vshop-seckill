package com.veli.vshop.seckill.dao.mapper;

import com.veli.vshop.seckill.dao.entity.TbSeckillGoods;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

public interface TbSeckillGoodsMapper extends Mapper<com.veli.vshop.seckill.dao.entity.TbSeckillGoods> {
    @Select(value = "select * from tb_seckill_goods where id = #{id} for update")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "goods_id", property = "goodsId"),
            @Result(column = "item_id", property = "itemId"),
            @Result(column = "title", property = "title"),
            @Result(column = "small_pic", property = "smallPic"),
            @Result(column = "price", property = "price"),
            @Result(column = "cost_price", property = "costPrice"),
            @Result(column = "seller_id", property = "sellerId"),
            @Result(column = "created_time", property = "createdTime"),
            @Result(column = "status", property = "status"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime"),
            @Result(column = "num", property = "num"),
            @Result(column = "mark", property = "mark"),
            @Result(column = "stock_count", property = "stockCount"),
            @Result(column = "goods_desc", property = "goodsDesc"),
            @Result(column = "introduction", property = "introduction"),
    })
    TbSeckillGoods selectByPrimaryKeyWithLock(@Param("id") Long id);

    /**
     * 悲观锁的另一种实现方式
     */
    @Update(value = "update tb_seckill_goods set stock_count=stock_count-1 where id=#{id} and stock_count>0")
    int updateByPrimaryKeyWithLock(@Param("id") Long id);

    @Update(value = "update tb_seckill_goods set stock_count=stock_count-1, version=version+1 where id = #{id} and version = #{version}")
    int updateByPrimaryKeyWithVersion(@Param("id") Long id, @Param("version") Integer version);
}