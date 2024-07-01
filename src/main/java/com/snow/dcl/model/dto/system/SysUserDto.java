/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.model.dto.system;

import com.snow.dcl.validation.GroupValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @ClassName SysUSerVo
 * (功能描述)
 * 用户参数对象Vo
 * @Author Dcl_Snow
 * @Create 2021/8/24 15:13
 * @Version 1.0.0
 */
@Data
public class SysUserDto {

    private Long id;

    @NotBlank(groups = GroupValidator.Create.class, message = "用户名称不能为空")
    private String username;

    private String password;

    @NotBlank(groups = GroupValidator.Create.class, message = "用户昵称不能为空")
    private String nickName;

    @NotBlank(groups = GroupValidator.Create.class, message = "用户手机号不能为空")
    private String phone;

    @Email
//    @NotBlank(groups = GroupValidator.Create.class, message = "用户邮箱不能为空")
    private String email;

//    @StringEnum(value = {"男", "女"}, message = "性别只允许为'男'或'女'")
    private Integer gender;

    private String avatar;
}
