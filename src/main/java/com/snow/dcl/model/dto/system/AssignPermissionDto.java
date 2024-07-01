package com.snow.dcl.model.dto.system;

import lombok.Data;

import java.util.List;

@Data
public class AssignPermissionDto {
    private Long roleId;

    private List<Long> permissionIdList;
}
