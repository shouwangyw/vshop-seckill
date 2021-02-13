package com.veli.vshop.seckill.order;

import com.veli.vshop.seckill.dao.entity.TbAddress;

/**
 * @author yangwei
 * @date 2021-02-11 09:22
 */
public interface AddressService {
    /**
     * 新增地址
     */
    boolean save(TbAddress address);
    /**
     * 更新地址
     */
    boolean update(TbAddress address);

}
