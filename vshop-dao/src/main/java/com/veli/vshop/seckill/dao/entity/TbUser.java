package com.veli.vshop.seckill.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * tb_user 用户表
 * 
 * @author yangwei
 */
@Data
@Accessors(chain = true)
public class TbUser {
    @Id
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("用户唯一标识")
    private String guid;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码，加密存储")
    private String password;

    @ApiModelProperty("注册手机号")
    private String phone;

    @ApiModelProperty("注册邮箱")
    private String email;

    @ApiModelProperty("创建时间")
    private Long createdTime;

    @ApiModelProperty("更新时间")
    private Long updatedTime;

    @ApiModelProperty("会员来源: PC、H5、Android、IOS、WeChat")
    private String sourceType;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("真实姓名")
    private String name;

    @ApiModelProperty("是否归档：0 否, 1 是")
    private Integer archived;

    @ApiModelProperty("头像地址")
    private String headPic;

    @ApiModelProperty("QQ号码")
    private String qq;

    @ApiModelProperty("账户余额")
    private Long accountBalance;

    @ApiModelProperty("手机是否验证: 0 否, 1是")
    private Integer isMobileCheck;

    @ApiModelProperty("邮箱是否检测: 0 否, 1 是")
    private Integer isEmailCheck;

    @ApiModelProperty("性别: F 女, M 男")
    private String sex;

    @ApiModelProperty("会员等级")
    private Integer userLevel;

    @ApiModelProperty("积分")
    private Integer points;

    @ApiModelProperty("经验值")
    private Integer experienceValue;

    @ApiModelProperty("生日")
    private String birthday;

    @ApiModelProperty("最后登录时间")
    private Long lastLoginTime;
}