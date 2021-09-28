package com.wuzhangze.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.cms.entity.CrmBanner;
import com.wuzhangze.cms.mapper.CrmBannerMapper;
import com.wuzhangze.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-03
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 根据banner创建时间 降序查询2条记录
     * @return
     */
    @Cacheable(key = "'selectIndexList'",value = "banner")
    @Override
    public List<CrmBanner> getAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create").last("limit 2");
        List<CrmBanner> banners = baseMapper.selectList(wrapper);
        return banners;
    }
}
