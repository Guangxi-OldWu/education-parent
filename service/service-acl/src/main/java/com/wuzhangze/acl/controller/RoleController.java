package com.wuzhangze.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhangze.acl.entity.Permission;
import com.wuzhangze.acl.entity.Role;
import com.wuzhangze.acl.entity.User;
import com.wuzhangze.acl.service.RoleService;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-09-19
 */
@RestController
@RequestMapping(ApiManager.V + "/acl/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "根据条件获取分页信息")
    @PostMapping("/page/{current}/{size}")
    public R pageUser(@PathVariable Integer current,@PathVariable Integer size,@RequestBody(required = false) Role role) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(role != null && !StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        Page<Role> page = new Page<>(current,size);
        return R.ok().list(roleService.page(page,wrapper).getRecords()).data("total",page.getTotal());
    }

    @ApiOperation(value = "获取所有角色信息")
    @GetMapping("/")
    public R findAll() {
        return R.ok().list(roleService.list());
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/add")
    public R addRole(@RequestBody Role role) {
        boolean save = roleService.save(role);
        return save ? R.ok() : R.err().message("添加角色失败");
    }

    @ApiOperation(value = "根据id删除角色")
    @DeleteMapping("/delete/{id}")
    public R removeRole(@PathVariable String id) {
        boolean b = roleService.removeById(id);
        return b ? R.ok() : R.err().message("删除角色失败");
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/update")
    public R updateRole(@RequestBody Role role) {
        roleService.updateById(role);
        return R.ok();
    }

    @ApiOperation(value = "批量删除角色")
    @DeleteMapping("/delete")
    public R deleteBatch(@RequestBody String[] ids) {
        ArrayList<String> idList = new ArrayList<>();
        for (String id : ids) {
            idList.add(id);
        }
        boolean b = roleService.removeByIds(idList);
        return b ? R.ok() : R.err().message("批量删除失败");
    }

    @ApiOperation(value = "根据角色id 获取该角色的权限")
    @GetMapping("/permission/{id}")
    public R getRolePermission(@PathVariable String id) {
        List<Permission> permissions = roleService.getRolePermissionById(id);
        return R.ok().list(permissions);
    }

}


