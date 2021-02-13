package com.veli.vshop.seckill.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * tb_areas 行政区域县区信息表
 * 
 * @author yangwei
 */
@Data
@Accessors(chain = true)
public class TbAreas {
    @Id
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("区域ID")
    private String areaId;

    @ApiModelProperty("区域名称")
    private String areaName;

    @ApiModelProperty("城市ID")
    private String cityId;
}