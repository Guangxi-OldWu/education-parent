package com.wuzhangze.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.acl.entity.Permission;
import com.wuzhangze.acl.entity.RolePermission;
import com.wuzhangze.acl.mapper.PermissionMapper;
import com.wuzhangze.acl.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuzhangze.acl.service.RolePermissionService;
import com.wuzhangze.servicebase.exception.LzException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-19
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public Permission findAllMenu() {
        List<Permission> permissions = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("type"));
        // 获取顶层权限
        Permission topPermission = permissions.get(0);
        permissions.remove(0);
        findAllPermissionByNode(topPermission,permissions);
        return topPermission;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void removePermission(String id) {
        List<Permission> permissions = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("type"));
        Permission node = null;
        for (Permission p : permissions) {
            if(p.getId().equals(id)) {
                node = p;
                break;
            }
        }
        if(node == null) {
            throw new LzException(20001,"找不到该菜单");
        }
        // 先给当前 permission找到他的子菜单
        findAllPermissionByNode(node,permissions);
        // 递归删除
        removePermissionByNode(node);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveRolePermission(String roleId, String[] permissionId) {
        // 先删除该用户的权限，再添加
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id",roleId));

        List<RolePermission> rolePermissions = new ArrayList<>();
        for (String pid : permissionId) {
            rolePermissions.add(new RolePermission(roleId,pid));
        }
        rolePermissionService.saveBatch(rolePermissions);
    }

    /**
     *
     * @param node 最高的权限对象
     * @param permissions 权限集合
     */
    public void findAllPermissionByNode(Permission node,List<Permission> permissions) {
        List<Permission> childs = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++) {
            Permission currentPermission = permissions.get(i);
            if(node.getId().equals(currentPermission.getPid())) {
                childs.add(currentPermission);
                findAllPermissionByNode(currentPermission,permissions);
            }
        }
        node.setChilds(childs);
    }

    /**
     * 递归删除菜单
     * @param node 菜单项，删除该菜单下的子菜单
     */
    public void removePermissionByNode(Permission node) {
        if(node.getChilds() == null) {
            baseMapper.deleteById(node.getId());
            return;
        }
        for (Permission child : node.getChilds()) {
            removePermissionByNode(child);
        }
        baseMapper.deleteById(node.getId());
    }
}
