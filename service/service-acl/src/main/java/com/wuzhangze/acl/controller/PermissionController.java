package com.wuzhangze.acl.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhangze.acl.entity.Permission;
import com.wuzhangze.acl.entity.Role;
import com.wuzhangze.acl.entity.User;
import com.wuzhangze.acl.service.PermissionService;
import com.wuzhangze.acl.service.RoleService;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-09-19
 */
@RestController
@RequestMapping(ApiManager.V + "/acl/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "获取分页信息")
    @GetMapping("/page/{current}/{size}")
    public R pageUser(@PathVariable Integer current,@PathVariable Integer size) {
        Page<Permission> page = new Page<>(current,size);
        return R.ok().list(permissionService.page(page).getRecords()).data("total",page.getTotal());
    }

    @ApiOperation(value = "获取所有权限信息，以树形结构返回 Permission -> childs -> childs")
    @GetMapping("/menu")
    public R findAll() {
        Permission permissions = permissionService.findAllMenu();
        return R.ok().obj(permissions);
    }

    @ApiOperation(value = "给角色添加权限")
    @PostMapping("/add/{rid}")
    public R addPermission(@PathVariable String rid,@RequestBody String[] permissionIds) {
        permissionService.saveRolePermission(rid,permissionIds);
        return R.ok();
    }

    @ApiOperation(value = "根据id删除菜单（因为有多层菜单，需要递归删除）")
    @DeleteMapping("/delete/{id}")
    public R removePermission(@PathVariable String id) {
        permissionService.removePermission(id);
        return R.ok();
    }


    @ApiOperation(value = "修改角色")
    @PutMapping("/update")
    public R updatePermission(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

}

