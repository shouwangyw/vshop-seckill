package com.veli.vshop.seckill.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * tb_seckill_goods 秒杀商品表
 * 
 * @author yangwei
 */
@Data
@Accessors(chain = true)
public class TbSeckillGoods {
    @Id
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("spu ID")
    private Long goodsId;

    @ApiModelProperty("sku ID")
    private Long itemId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("商品图片")
    private String smallPic;

    @ApiModelProperty("原价格")
    private BigDecimal price;

    @ApiModelProperty("秒杀价格")
    private BigDecimal costPrice;

    @ApiModelProperty("商家ID")
    private String sellerId;

    @ApiModelProperty("添加日期")
    private Long createdTime;

    @ApiModelProperty("审核日期")
    private Long checkTime;

    @ApiModelProperty("审核状态")
    private Integer status;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

    @ApiModelProperty("秒杀商品数")
    private Integer num;

    @ApiModelProperty("标签")
    private String mark;

    @ApiModelProperty("剩余库存数")
    private Integer stockCount;

    @ApiModelProperty("商品描述")
    private String goodsDesc;

    @ApiModelProperty("介绍")
    private String introduction;

    @ApiModelProperty("事务状态: 0 初始, 1 成功, -1 回滚")
    private Integer transactionStatus;

    @ApiModelProperty("版本")
    private Integer version;
}