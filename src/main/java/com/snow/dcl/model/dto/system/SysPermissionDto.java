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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @ClassName SysPermissionVo
 * (功能描述)
 * 权限参数对象Vo
 * @Author Dcl_Snow
 * @Create 2021/8/27 11:28
 * @Version 1.0.0
 */
@ApiModel(value="SysPermissionVo", description="权限参数对象")
@Data
public class SysPermissionDto {

    @ApiModelProperty(value = "权限id")
    @Null(groups = GroupValidator.Create.class, message = "id必须为null")
    @NotNull(groups = GroupValidator.Update.class, message = "id不能为null")
    private Long id;

    @ApiModelProperty(value = "权限pid")
    @NotNull(groups = {GroupValidator.Create.Create.class, GroupValidator.Update.class}, message = "id不能为null")
    private Long pid;

    @ApiModelProperty(value = "权限名称")
    @NotBlank(groups = GroupValidator.Create.class, message = "权限名称不能为空")
    private String name;

    @ApiModelProperty(value = "权限类型")
    @NotNull(groups = GroupValidator.Create.class, message = "权限类型不能为null")
    private Integer type;

    @ApiModelProperty(value = "权限值")
    private String permissionValue;

    @ApiModelProperty(value = "权限访问路径")
    @NotBlank(groups = GroupValidator.Create.class, message = "权限访问路径不能为空")
    private String path;

    @ApiModelProperty(value = "权限组件路径")
    @NotBlank(groups = GroupValidator.Create.class, message = "权限组件路径不能为空")
    private String component;

    @ApiModelProperty(value = "权限图标")
    private String icon;
}
