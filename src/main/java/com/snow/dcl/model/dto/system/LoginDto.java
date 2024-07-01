/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.model.dto.system;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @ClassName LoginVo
 * (功能描述)
 * 登录用户
 * @Author Dcl_Snow
 * @Create 2021/11/19 14:11
 * @Version 1.0.0
 */
@Data
public class LoginDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String codeKey;

    @NotNull
    private String captcha;
}
