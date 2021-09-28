package com.wuzhangze.statistics.controller;


import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.api.ApiManager;
import com.wuzhangze.statistics.client.UcenterClient;
import com.wuzhangze.statistics.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-09-14
 */
@RestController
@RequestMapping(ApiManager.V + "/statistics")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation(value = "统计某一天的注册人数")
    @PostMapping("/count/registry/{day}")
    public R getRegistryCountByDay(@PathVariable String day) {
        Integer count = statisticsDailyService.getRegistryCountByDay(day);
        return R.ok().data("count",count);
    }

    @ApiOperation(value = "根据开始日期和结束日期，查询指定列的数据")
    @GetMapping("/{type}/{start}/{end}")
    public R getStatisticsByType(
            @ApiParam(value = "数据库列名",example = "register_num") @PathVariable String type,
            @ApiParam(value = "开始日期",example = "2020-01-01") @PathVariable String start,
            @ApiParam(value = "结束日期",example = "2020-01-04") @PathVariable String end){

        Map map = statisticsDailyService.getStatisticsByType(type,start,end);
        return R.ok().data(map);
    }
}

