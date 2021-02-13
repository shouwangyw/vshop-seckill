package com.veli.vshop.seckill.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yangwei
 */
@Data
@Accessors(chain = true)
public class BaseUser {
    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码，加密存储")
    private String password;
    @ApiModelProperty("注册手机号")
    private String phone;
    @ApiModelProperty("注册邮箱")
    private String email;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("真实姓名")
    private String name;
    @ApiModelProperty("头像地址")
    private String headPic;
    @ApiModelProperty("QQ号码")
    private String qq;
    @ApiModelProperty("性别: F 女, M 男")
    private String sex;
    @ApiModelProperty("生日")
    private String birthday;
}
