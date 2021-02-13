package com.veli.vshop.seckill.controller;

import com.veli.vshop.seckill.dao.entity.TbAddress;
import com.veli.vshop.seckill.order.AddressService;
import com.veli.vshop.seckill.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yangwei
 */
@Api(tags = "地址模块")
@RestController
@RequestMapping("/address")
public class AddressController {
    @Resource
    private AddressService addressService;

    @ApiOperation("新增地址")
    @PostMapping("/save")
    public RestResponse<Boolean> save(@RequestBody TbAddress address) {
        return RestResponse.success(addressService.save(address));
    }
}
