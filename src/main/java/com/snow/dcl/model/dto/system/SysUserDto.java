/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.model.dto.system;

import com.snow.dcl.validation.GroupValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @ClassName SysUSerVo
 * (功能描述)
 * 用户参数对象Vo
 * @Author Dcl_Snow
 * @Create 2021/8/24 15:13
 * @Version 1.0.0
 */
@ApiModel(value="SysUserVo", description="用户参数对象")
@Data
public class SysUserDto {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户名称")
    @NotBlank(groups = GroupValidator.Create.class, message = "用户名称不能为空")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户昵称")
    @NotBlank(groups = GroupValidator.Create.class, message = "用户昵称不能为空")
    private String nickName;

    @ApiModelProperty(value = "用户手机号")
    @NotBlank(groups = GroupValidator.Create.class, message = "用户手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "用户邮箱")
    @Email
//    @NotBlank(groups = GroupValidator.Create.class, message = "用户邮箱不能为空")
    private String email;

    @ApiModelProperty(value = "用户性别")
//    @StringEnum(value = {"男", "女"}, message = "性别只允许为'男'或'女'")
    private Integer gender;

    @ApiModelProperty(value = "用户头像")
    private String avatar;
}
