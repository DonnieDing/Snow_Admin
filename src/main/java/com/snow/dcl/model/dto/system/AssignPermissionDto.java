package com.snow.dcl.model.dto.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(description = "分配权限")
@Data
public class AssignPermissionDto {
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "权限id列表")
    private List<Long> permissionIdList;
}
