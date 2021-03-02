package com.veli.vshop.seckill.queue.disruptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 秒杀事件对象
 * @author yangwei
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SeckillEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private long seckillId;
    private String userId;
}
