package com.veli.vshop.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author yangwei
 * @date 2021-02-06 12:10
 */
@SpringBootApplication
@MapperScan("com.veli.vshop.seckill.dao.mapper")
public class SeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }
}
