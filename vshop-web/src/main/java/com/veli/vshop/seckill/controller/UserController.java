package com.veli.vshop.seckill.controller;

import com.veli.vshop.seckill.dao.entity.TbUser;
import com.veli.vshop.seckill.domain.BaseUser;
import com.veli.vshop.seckill.response.RestResponse;
import com.veli.vshop.seckill.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangwei
 * @date 2021-02-06 22:56
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public RestResponse<Boolean> register(@RequestBody BaseUser user) {
        return RestResponse.success(userService.register(user));
    }

    @ApiOperation("用户基本信息修改")
    @PostMapping("/update")
    public RestResponse<Boolean> update(@RequestBody BaseUser user) {
        return RestResponse.success(userService.updateUser(user));
    }

    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public RestResponse<String> login(@RequestBody BaseUser user) {
        return RestResponse.success(userService.login(user));
    }

    @ApiOperation("根据Token获取用户信息")
    @GetMapping("/{token}")
    public RestResponse<BaseUser> getUserInfo(@PathVariable String token) {
        return RestResponse.success(userService.queryUserByToken(token));
    }

    @ApiOperation("查询所有用户信息")
    @GetMapping("/all")
    public RestResponse<List<TbUser>> queryAllUser() {
        return RestResponse.success(userService.queryAllUser());
    }
}
