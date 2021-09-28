package com.wuzhangze.acl.service;

import com.wuzhangze.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-19
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 获取所有菜单信息，层级返回 -> Permission -> Permission.child
     * @return
     */
    Permission findAllMenu();

    /**
     * 根据id删除菜单
     * @param id
     */
    void removePermission(String id);

    /**
     * 保存角色权限
     * @param roleId    角色id
     * @param permissionId 权限id数组
     */
    void saveRolePermission(String roleId,String[] permissionId);
}
