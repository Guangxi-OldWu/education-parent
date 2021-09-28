package com.wuzhangze.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.commonutil.JwtUtils;
import com.wuzhangze.commonutil.MD5;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.exception.LzException;
import com.wuzhangze.ucenter.client.SmsClient;
import com.wuzhangze.ucenter.entity.Member;
import com.wuzhangze.ucenter.entity.vo.LoginVo;
import com.wuzhangze.ucenter.mapper.MemberMapper;
import com.wuzhangze.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuzhangze.ucenter.entity.vo.RegistryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-04
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 判断手机号和密码进行登录，密码使用MD5进行加密再从数据库搜索
     * 被封的人不能登录
     * @param member
     * @return
     */
    @Override
    public String login(LoginVo member) {
        String mobile = member.getMobile();
        String password = MD5.encrypt(member.getPassword());
        if(StringUtils.isEmpty(mobile.trim()) || StringUtils.isEmpty(password.trim())) {
            throw new LzException(20001,"登陆失败");
        }
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile).eq("password",password);
        Member result = baseMapper.selectOne(wrapper);
        if(result == null) {
            throw new LzException(20001,"手机号或密码错误，请重新输入");
        }
        if(result.getIsDisabled()) {
            throw new LzException(20001,"你已被系统封禁");
        }
        String token = JwtUtils.getJwtToken(result.getId(), result.getNickname());
        return token;
    }

    /**
     * 会员注册，判断验证码
     * @param code
     * @param registryVo
     * @return
     */
    @Override
    public boolean registry(String code, RegistryVo registryVo) {
        String c = stringRedisTemplate.opsForValue().get(registryVo.getMobile());
        String phone = registryVo.getMobile();

        if(StringUtils.isEmpty(c)) {
            throw new LzException(20001,"验证码有误或已失效");
        }

        if(!StringUtils.isEmpty(c) && c.equals(code)) {
            Member member = new Member();
            BeanUtils.copyProperties(registryVo, member);
            member.setPassword(MD5.encrypt(member.getPassword()));
            member.setAvatar("https://edu-shenzhen.oss-cn-shenzhen.aliyuncs.com/edu/avatar/comment_detalt_avartar.png");

            QueryWrapper<Member> wrapper = new QueryWrapper<Member>().eq("mobile", phone);
            if(baseMapper.selectCount(wrapper) > 0) {
                throw new LzException(20001,"失败：此手机号已被注册！");
            }

            int insert = baseMapper.insert(member);
            if(insert > 0) stringRedisTemplate.delete(member.getMobile());

            return insert > 0;
        }
        return false;
    }

    @Override
    public int getRegistryCountByDay(String day) {
        return baseMapper.selectRegistryCountByDay(day);
    }
}
