package com.snow.dcl.utils;

import com.snow.dcl.model.SysPermission;
import com.snow.dcl.model.dto.system.MetaDto;
import com.snow.dcl.model.dto.system.RouterDto;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (功能描述)
 * 根据菜单构建路由
 * @Author:Dcl_Snow
 * @Create: 2023/8/1 15:30
 * @version: 1.0.0
 */
public class RouterHelper {

    /**
     * 根据菜单构建路由
     * @param menus
     * @return
     */
    public static List<RouterDto> buildRouters(Set<SysPermission> menus) {
        List<RouterDto> routers = new LinkedList<>();
        for (SysPermission menu : menus) {
            RouterDto router = new RouterDto();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaDto(menu.getName(), menu.getIcon()));
            Set<SysPermission> children = menu.getChildren().stream().collect(Collectors.toSet());
            //如果当前是菜单，需将按钮对应的路由加载出来，如：“角色授权”按钮对应的路由在“系统管理”下面
            if(menu.getType() == 1) {
                List<SysPermission> hiddenMenuList = children.stream().filter(item -> !StringUtils.isEmpty(item.getComponent())).collect(Collectors.toList());
                for (SysPermission hiddenMenu : hiddenMenuList) {
                    RouterDto hiddenRouter = new RouterDto();
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaDto(hiddenMenu.getName(), hiddenMenu.getIcon()));
                    routers.add(hiddenRouter);
                }
            } else {
                if (!CollectionUtils.isEmpty(children)) {
                    if(children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(buildRouters(children));
                }
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public static String getRouterPath(SysPermission menu) {
        String routerPath = "/" + menu.getPath();
        if(menu.getPid().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }
}
