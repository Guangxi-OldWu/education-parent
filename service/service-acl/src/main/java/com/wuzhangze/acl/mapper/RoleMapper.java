package com.wuzhangze.acl.mapper;

import com.wuzhangze.acl.entity.Permission;
import com.wuzhangze.acl.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author OldWu
 * @since 2021-09-19
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取角色权限
     * @param id
     * @return
     */
    List<Permission> selectRolePermissionById(String id);
}
