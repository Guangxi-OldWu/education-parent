package com.wuzhangze.acl.mapper;

import com.wuzhangze.acl.entity.Role;
import com.wuzhangze.acl.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author OldWu
 * @since 2021-09-19
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户id 查询用户权限
     * @param id
     * @return
     */
    List<Role> selectUserRoleById(String id);
}
