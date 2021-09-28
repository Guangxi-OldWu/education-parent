package com.wuzhangze.statistics.service;

import com.wuzhangze.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-14
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 根据日期统计该天注册人数
     * @param day 2020-01-01
     * @return
     */
    Integer getRegistryCountByDay(String day);

    /**
     * 根据开始日期和结束日期，查询指定列的数据
     * @param type 数据库列名 register_num
     * @param start 2020-01-01
     * @param end 2020-01-03
     * @return
     */
    Map getStatisticsByType(String type, String start, String end);
}
