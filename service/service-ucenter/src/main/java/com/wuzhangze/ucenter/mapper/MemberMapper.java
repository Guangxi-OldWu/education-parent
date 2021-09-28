package com.wuzhangze.ucenter.mapper;

import com.wuzhangze.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author OldWu
 * @since 2021-09-04
 */
public interface MemberMapper extends BaseMapper<Member> {
    Integer selectRegistryCountByDay(String day);
}
