package com.wuzhangze.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.acl.entity.Role;
import com.wuzhangze.acl.entity.User;
import com.wuzhangze.acl.entity.UserRole;
import com.wuzhangze.acl.mapper.UserMapper;
import com.wuzhangze.acl.service.UserRoleService;
import com.wuzhangze.acl.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleService userRoleService;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void addUserRole(String uid, String[] roleIds) {
        // 先删除该用户的角色再插入，不然可能重复
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id",uid));

        List<UserRole> userRoles = new ArrayList<>();
        for (String roleId : roleIds) {
            userRoles.add(new UserRole(roleId,uid));
        }
        userRoleService.saveBatch(userRoles);
    }

    @Override
    public List<Role> getUserRoleById(String id) {
        List<Role> roles = baseMapper.selectUserRoleById(id);
        return roles;
    }
}
