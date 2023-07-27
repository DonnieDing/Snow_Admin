package com.snow.dcl.utils;

import com.snow.dcl.model.SysPermission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName MenuHelper
 * (功能描述)
 * 菜单构建工具
 * @Author Dcl_Snow
 * @Create 2023/7/27 10:22
 * @Version 1.0.0
 */
public class MenuHelper {
    public static Set<SysPermission> buildTree(Set<SysPermission> permissionList) {
        Set<SysPermission> trees = new HashSet<>();
        for (SysPermission permissionNode : permissionList) {
            if (permissionNode.getPid() == 0) {
                permissionNode.setLevel(1);
                trees.add(selectChildren(permissionNode, permissionList));
            }
        }
        return trees;
    }

    private static SysPermission selectChildren(SysPermission permissionNode, Set<SysPermission> permissionList) {
        // permissionNode.setChildren(new ArrayList<>());
        for (SysPermission aclPermission : permissionList) {
            if (aclPermission.getPid().equals(permissionNode.getId())) {
                aclPermission.setLevel(permissionNode.getLevel() + 1);
                if (permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<>());
                }
                permissionNode.getChildren().add(selectChildren(aclPermission, permissionList));
            }
        }
        return permissionNode;
    }
}
