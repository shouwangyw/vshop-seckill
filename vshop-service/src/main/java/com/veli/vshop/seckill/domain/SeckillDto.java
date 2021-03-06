package com.veli.vshop.seckill.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author yangwei
 * @date 2021-03-06 18:35
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SeckillDto {
    private Long seckillId;
    private String userId;
}
