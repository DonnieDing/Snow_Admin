/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName LoginVo
 * (功能描述)
 * 登录用户
 * @Author Dcl_Snow
 * @Create 2021/11/19 14:11
 * @Version 1.0.0
 */
@ApiModel(value="LoginVo", description="用户登录参数对象")
@Data
public class LoginVo {

    @ApiModelProperty(value = "用户名称")
    @NotNull
    private String username;

    @NotNull
    @ApiModelProperty(value = "用户密码")
    private String password;

    @NotNull
    @ApiModelProperty(value = "验证码Key")
    private String codeKey;

    @NotNull
    @ApiModelProperty(value = "验证码")
    private String captcha;
}
