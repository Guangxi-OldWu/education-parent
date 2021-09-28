package com.wuzhangze.ucenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.commonutil.JwtUtils;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.api.ApiManager;
import com.wuzhangze.servicebase.dto.MemberOrderInfo;
import com.wuzhangze.servicebase.exception.LzException;
import com.wuzhangze.ucenter.entity.Member;
import com.wuzhangze.ucenter.entity.vo.LoginVo;
import com.wuzhangze.ucenter.entity.vo.MemberVo;
import com.wuzhangze.ucenter.service.MemberService;
import com.wuzhangze.ucenter.entity.vo.RegistryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-09-04
 */
@RestController
@RequestMapping(ApiManager.V + "/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public R login(@Valid @RequestBody LoginVo member) {
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("/registry")
    public R registry(@Valid @RequestBody RegistryVo member) {
        boolean registry = memberService.registry(member.getCode(), member);
        return registry ? R.ok() : R.err().message("注册失败");
    }

    @ApiOperation(value = "根据 token 获取用户信息")
    @GetMapping("/get")
    public R getUserInfO(HttpServletRequest request) {

        String id = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(id)) {
            throw new LzException(20001,"请传入正确的token");
        }

        Member member = memberService.getById(id);
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(member,memberVo);
        return R.ok().obj(memberVo);
    }

    @ApiOperation(value = "根据 token 获取用户信息")
    @GetMapping("/get/{token}")
    public R getUserInfO(@PathVariable("token") String token) {

        String id = JwtUtils.getMemberIdByJwtToken(token);
        if(StringUtils.isEmpty(id)) {
            throw new LzException(20001,"请传入正确的token");
        }

        Member member = memberService.getById(id);
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(member,memberVo);
        return R.ok().obj(memberVo);
    }

    @ApiOperation(value = "根据用户id返回用户信息")
    @GetMapping("/{id}")
    public MemberOrderInfo getMemberOrderInfo(@PathVariable("id") String id) {
        Member member = memberService.getById(id);
        MemberOrderInfo memberOrderInfo = new MemberOrderInfo();
        BeanUtils.copyProperties(member,memberOrderInfo);
        return memberOrderInfo;
    }

    @ApiOperation(value = "查询某一天的注册认数")
    @GetMapping("/count/{day}")
    public R countRegistry(@PathVariable String day) {
        Integer count = memberService.getRegistryCountByDay(day);
        return R.ok().data("count",count);
    }
}

