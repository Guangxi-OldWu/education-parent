package com.wuzhangze.acl.service;

import com.wuzhangze.acl.entity.Role;
import com.wuzhangze.acl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-19
 */
public interface UserService extends IService<User> {

    /**
     * 给用户添加角色
     * @param uid   用户id
     * @param roleIds   角色列表
     */
    void addUserRole(String uid, String[] roleIds);

    /**
     * 根据用户id 查询该用户的权限
     * @param id
     * @return
     */
    List<Role> getUserRoleById(String id);
}
