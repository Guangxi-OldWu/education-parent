package com.wuzhangze.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhangze.acl.entity.Permission;
import com.wuzhangze.acl.entity.Role;
import com.wuzhangze.acl.entity.User;
import com.wuzhangze.acl.service.PermissionService;
import com.wuzhangze.acl.service.UserService;
import com.wuzhangze.acl.vo.UserVo;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-09-19
 */
@RestController
@RequestMapping(ApiManager.V + "/acl/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据条件获取分页信息")
    @PostMapping("/page/{current}/{size}")
    public R pageUser(@PathVariable Integer current,@PathVariable Integer size,@RequestBody(required = false) User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(user != null && !StringUtils.isEmpty(user.getUsername())) {
            wrapper.like("username",user.getUsername());
        }
        Page<User> page = new Page<>(current,size);
        return R.ok().list(userService.page(page,wrapper).getRecords()).data("total",page.getTotal());
    }

    @ApiOperation(value = "获取所有用户信息")
    @GetMapping("/")
    public R findAll() {
        return R.ok().list(userService.list());
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/add")
    public R addPermission(@Valid @RequestBody UserVo user) {
        User user1 = new User();
        BeanUtils.copyProperties(user,user1);
        boolean save = userService.save(user1);
        return save ? R.ok() : R.err().message("添加用户失败");
    }

    @ApiOperation(value = "根据id删除用户")
    @DeleteMapping("/delete/{id}")
    public R removePermission(@PathVariable String id) {
        boolean b = userService.removeById(id);
        return b ? R.ok() : R.err().message("删除用户失败");
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("/update")
    public R updatePermission(@Valid @RequestBody User user) {
        userService.updateById(user);
        return R.ok();
    }

    @ApiOperation(value = "给用户添加角色")
    @PostMapping("/add/role/{uid}")
    public R userAddRole(@PathVariable String uid,@RequestBody String[] roleIds) {
        userService.addUserRole(uid,roleIds);
        return R.ok();
    }

    @ApiOperation(value = "批量删除用户")
    @DeleteMapping("/delete")
    public R deleteBatch(@RequestBody String[] ids) {
        ArrayList<String> idList = new ArrayList<>();
        for (String id : ids) {
            idList.add(id);
        }
        boolean b = userService.removeByIds(idList);
        return b ? R.ok() : R.err().message("批量删除失败");
    }

    @ApiOperation(value = "根据用户id 获取该用户的角色")
    @GetMapping("/role/{id}")
    public R getUserRole(@PathVariable String id) {
        List<Role> roles = userService.getUserRoleById(id);
        return R.ok().list(roles);
    }
}

