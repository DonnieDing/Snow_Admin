/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.model.dto.system;

import com.snow.dcl.validation.GroupValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @ClassName SysRoleVo
 * (功能描述)
 * 角色参数对象Vo
 * @Author Dcl_Snow
 * @Create 2021/8/27 10:12
 * @Version 1.0.0
 */
@Data
public class SysRoleDto {

    private Long id;

    @NotBlank(groups = GroupValidator.Create.class, message = "角色名称不能为空")
    private String roleName;

    private String roleCode;

    private String remark;
}
