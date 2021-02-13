package com.veli.vshop.seckill.user;

import com.veli.vshop.seckill.dao.entity.TbUser;
import com.veli.vshop.seckill.domain.BaseUser;

import java.util.List;

/**
 * @author yangwei
 */
public interface UserService {
    /**
     * 注册用户
     */
    boolean register(BaseUser user);
    /**
     * 更改用户信息
     */
    boolean updateUser(BaseUser user);
    /**
     * 查询所有用户信息
     */
    List<TbUser> queryAllUser();
    /**
     * 登录
     * @return token
     */
    String login(BaseUser user);
    /**
     * 根据token查询用户信息
     */
    BaseUser queryUserByToken(String token);
}
