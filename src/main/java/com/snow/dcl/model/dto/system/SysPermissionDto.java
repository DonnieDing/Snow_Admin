/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.model.dto.system;

import com.snow.dcl.validation.GroupValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

/**
 * @ClassName SysPermissionVo
 * (功能描述)
 * 权限参数对象Vo
 * @Author Dcl_Snow
 * @Create 2021/8/27 11:28
 * @Version 1.0.0
 */
@Data
public class SysPermissionDto {

    @Null(groups = GroupValidator.Create.class, message = "id必须为null")
    @NotNull(groups = GroupValidator.Update.class, message = "id不能为null")
    private Long id;

    @NotNull(groups = {GroupValidator.Create.Create.class, GroupValidator.Update.class}, message = "id不能为null")
    private Long pid;

    @NotBlank(groups = GroupValidator.Create.class, message = "权限名称不能为空")
    private String name;

    @NotNull(groups = GroupValidator.Create.class, message = "权限类型不能为null")
    private Integer type;

    private String permissionValue;

    @NotBlank(groups = GroupValidator.Create.class, message = "权限访问路径不能为空")
    private String path;

    @NotBlank(groups = GroupValidator.Create.class, message = "权限组件路径不能为空")
    private String component;

    private String icon;
}
