package com.wuzhangze.ucenter.service;

import com.wuzhangze.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuzhangze.ucenter.entity.vo.LoginVo;
import com.wuzhangze.ucenter.entity.vo.RegistryVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-04
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo member);

    boolean registry(String code, RegistryVo registryVo);

    /**
     * 根据日期获取该天注册的人数
     * @param day 2020-01-01
     * @return 该日期注册人数
     */
    int getRegistryCountByDay(String day);
}
