package com.veli.vshop.seckill.order;

import com.veli.vshop.seckill.dao.entity.TbAddress;
import com.veli.vshop.seckill.dao.mapper.TbAddressMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yangwei
 */
@Slf4j
@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private TbAddressMapper tbAddressMapper;

    @Override
    public boolean save(TbAddress address) {
        return false;
    }

    @Override
    public boolean update(TbAddress address) {
        return false;
    }
}
