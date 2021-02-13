package com.veli.vshop.seckill.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * tb_seckill_order 秒杀商品表
 * 
 * @author yangwei
 */
@Data
@Accessors(chain = true)
public class TbSeckillOrder {
    @Id
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("秒杀商品ID")
    private Long seckillId;

    @ApiModelProperty("支付金额")
    private BigDecimal money;

    @ApiModelProperty("用户")
    private String userId;

    @ApiModelProperty("商家")
    private String sellerId;

    @ApiModelProperty("创建时间")
    private Long createdTime;

    @ApiModelProperty("支付时间")
    private Long payTime;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("收货人地址")
    private String receiverAddress;

    @ApiModelProperty("收货人电话")
    private String receiverMobile;

    @ApiModelProperty("收货人")
    private String receiver;

    @ApiModelProperty("交易流水")
    private String transactionId;
}