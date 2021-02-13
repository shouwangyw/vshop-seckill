package com.veli.vshop.seckill.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseCriteria {
    @ApiModelProperty("第几页")
    private int pageNum = 1;
    @ApiModelProperty("每页多少条")
    private int pageSize = 10;
    @ApiModelProperty("排序方式 如：created_time desc")
    private String orderColumn;
}