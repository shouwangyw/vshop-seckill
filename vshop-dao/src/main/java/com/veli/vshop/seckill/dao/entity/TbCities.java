package com.veli.vshop.seckill.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * tb_cities 城市表
 * 
 * @author yangwei
 */
@Data
@Accessors(chain = true)
public class TbCities {
    @Id
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("城市ID")
    private String cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("省份ID")
    private String provinceId;
}