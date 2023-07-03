/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.model.vo;

import com.snow.dcl.validation.GroupValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName SysRoleVo
 * (功能描述)
 * 角色参数对象Vo
 * @Author Dcl_Snow
 * @Create 2021/8/27 10:12
 * @Version 1.0.0
 */
@ApiModel(value="SysRoleVo", description="角色参数对象")
@Data
public class SysRoleVo {

    @ApiModelProperty(value = "角色id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(groups = GroupValidator.Create.class, message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty(value = "角色码")
    private String roleCode;

    @ApiModelProperty(value = "角色备注")
    private String remark;
}
