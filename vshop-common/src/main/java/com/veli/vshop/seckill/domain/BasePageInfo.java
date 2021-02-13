package com.veli.vshop.seckill.domain;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class BasePageInfo<T> {
    @ApiModelProperty("数据列表")
    private List<T> dataList;
    @ApiModelProperty("总条数")
    private long totalCount;

    public BasePageInfo(PageInfo<T> pageInfo) {
        this.dataList = pageInfo.getList();
        this.totalCount = pageInfo.getTotal();
    }
    public BasePageInfo(List<T> dataList) {
        this.dataList = dataList;
    }
}