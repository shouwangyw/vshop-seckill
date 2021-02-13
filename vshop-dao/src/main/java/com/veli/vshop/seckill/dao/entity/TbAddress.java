package com.veli.vshop.seckill.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * tb_address 地址表
 * 
 * @author yangwei
 */
@Data
@Accessors(chain = true)
public class TbAddress {
    @Id
    private Long id;

    @ApiModelProperty("用户GUID")
    private String userId;

    @ApiModelProperty("省")
    private String provinceId;

    @ApiModelProperty("市")
    private String cityId;

    @ApiModelProperty("县/区")
    private String townId;

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("联系人")
    private String contact;

    @ApiModelProperty("是否是默认: 1 默认, 0 否")
    private Integer isDefault;

    @ApiModelProperty("备注")
    private String notes;

    @ApiModelProperty("创建时间")
    private Long createdTime;

    @ApiModelProperty("别名")
    private String alias;
}