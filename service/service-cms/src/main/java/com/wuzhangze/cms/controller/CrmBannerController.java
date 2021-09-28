package com.wuzhangze.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhangze.cms.entity.CrmBanner;
import com.wuzhangze.cms.service.CrmBannerService;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-09-03
 */
// TODO 添加banner时存到oss，删除banner时删除对应的oss 图片
@RestController
@RequestMapping(ApiManager.V + "/cms/banner")
public class CrmBannerController {

    @Resource
    private CrmBannerService bannerService;

    @ApiOperation(value = "分页查询banner")
    @GetMapping("/pages/{current}/{size}")
    public R getBannerPage(@PathVariable Integer current,@PathVariable Integer size) {
        Page<CrmBanner> page = bannerService.page(new Page<CrmBanner>(current, size));
        return R.ok().list(page.getRecords()).data("total",page.getTotal());
    }

    @ApiOperation(value = "根据id查询banner")
    @GetMapping("/{id}")
    public R getBanner(@PathVariable Integer id) {
        return R.ok().obj(bannerService.getById(id));
    }

    @ApiOperation(value = "获取首页两条Banner")
    @GetMapping("/")
    public R findAll() {
        return R.ok().list(bannerService.getAllBanner());
    }

    @ApiOperation(value = "添加banner")
    @PostMapping("/add")
    public R addBanner(@RequestBody CrmBanner banner) {
        return bannerService.save(banner) ? R.ok() : R.err();
    }

    @CacheEvict(value = "banner")
    @ApiOperation(value = "根据id修改banner")
    @PutMapping("/update")
    public R updateBanner(@RequestBody CrmBanner banner) {
        return bannerService.updateById(banner) ? R.ok() : R.err();
    }

    @CacheEvict(value = "banner")
    @ApiOperation(value = "删除banner")
    @DeleteMapping("/delete/{id}")
    public R deleteBanner(@PathVariable String id) {
        return bannerService.removeById(id) ? R.ok() : R.err();
    }
}

