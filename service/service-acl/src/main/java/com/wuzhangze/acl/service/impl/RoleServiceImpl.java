package com.wuzhangze.acl.service.impl;

import com.wuzhangze.acl.entity.Permission;
import com.wuzhangze.acl.entity.Role;
import com.wuzhangze.acl.mapper.RoleMapper;
import com.wuzhangze.acl.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Permission> getRolePermissionById(String id) {
        return baseMapper.selectRolePermissionById(id);
    }
}
