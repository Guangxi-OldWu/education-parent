package com.wuzhangze.acl.service;

import com.wuzhangze.acl.entity.Permission;
import com.wuzhangze.acl.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-19
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取角色权限
     * @param id 角色id
     * @return
     */
    List<Permission> getRolePermissionById(String id);
}
