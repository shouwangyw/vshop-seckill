package com.veli.vshop.seckill.user;

import com.veli.vshop.seckill.dao.entity.TbUser;
import com.veli.vshop.seckill.dao.mapper.TbUserMapper;
import com.veli.vshop.seckill.domain.BaseUser;
import com.veli.vshop.seckill.domain.SourceType;
import com.veli.vshop.seckill.exception.CustomException;
import com.veli.vshop.seckill.redis.RedisService;
import com.veli.vshop.seckill.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private TbUserMapper userMapper;
    @Resource
    private RedisService redisService;

    @Override
    public boolean register(BaseUser user) {
        val tbUser = new TbUser()
                .setGuid(CommonUtils.guid())
                .setUsername(user.getUsername())
                .setPassword(CommonUtils.md5(user.getPassword()))
                .setPhone(user.getPhone())
                .setEmail(user.getEmail())
                .setCreatedTime(System.currentTimeMillis())
                .setUpdatedTime(System.currentTimeMillis())
                .setSourceType(SourceType.PC.toString())
                .setNickName(user.getNickName())
                .setName(user.getName())
                .setHeadPic(user.getHeadPic())
                .setQq(user.getQq())
                .setSex(user.getSex())
                .setBirthday(user.getBirthday());
        return userMapper.insertSelective(tbUser) >= 1;
    }

    public static void main(String[] args) {
        System.out.println(CommonUtils.guid());
    }
    @Override
    public boolean updateUser(BaseUser user) {
        val tbUser = new TbUser()
                .setId(user.getId())
                .setPassword(CommonUtils.md5(user.getPassword()))
                .setUpdatedTime(System.currentTimeMillis())
                .setNickName(user.getNickName())
                .setName(user.getName())
                .setHeadPic(user.getHeadPic())
                .setQq(user.getQq())
                .setSex(user.getSex())
                .setBirthday(user.getBirthday());
        return userMapper.updateByPrimaryKeySelective(tbUser) >= 1;
    }

    @Override
    public String login(BaseUser user) {
        val queryUser = new TbUser()
                .setUsername(user.getUsername())
                .setPassword(CommonUtils.md5(user.getPassword()));
        TbUser tbUser = userMapper.selectOne(queryUser);
        if (tbUser == null) {
            throw new CustomException("用户名密码错误");
        }
        // 生成token
        String token = CommonUtils.guid();

        val baseUser = new BaseUser();
        BeanUtils.copyProperties(tbUser, baseUser);

        redisService.setObjValue(token, baseUser, 1, TimeUnit.HOURS);

        return token;
    }

    @Override
    public BaseUser queryUserByToken(String token) {
        return redisService.getObjValue(token);
    }

    @Override
    public List<TbUser> queryAllUser() {
        return userMapper.selectAll();
    }
}