package com.wuzhangze.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.statistics.client.UcenterClient;
import com.wuzhangze.statistics.entity.StatisticsDaily;
import com.wuzhangze.statistics.mapper.StatisticsDailyMapper;
import com.wuzhangze.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-14
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Integer getRegistryCountByDay(String day) {
        Integer count = (Integer) ucenterClient.countRegistry(day).getData().get("count");
        Integer tag = baseMapper.selectCount(new QueryWrapper<StatisticsDaily>().eq("date_calculated", day));
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(count);
        daily.setLoginNum(RandomUtils.nextInt(100,500));
        daily.setCourseNum(RandomUtils.nextInt(0,20));
        daily.setVideoViewNum(RandomUtils.nextInt(1000,50000));
        daily.setDateCalculated(day);
        if(tag > 0) {
            // 已经有统计数据了，删除该日期统计数据
            baseMapper.delete(new QueryWrapper<StatisticsDaily>().eq("date_calculated", day));
            // 插入新的统计数据
            baseMapper.insert(daily);
        }else {
            baseMapper.insert(daily);
        }
        return count;
    }

    @Override
    public Map getStatisticsByType(String type, String start, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated",type).between("date_calculated",start,end);
        List<StatisticsDaily> data = baseMapper.selectList(wrapper);

        List<Integer> value = new ArrayList<>();
        List<String> name = data.stream().map(StatisticsDaily::getDateCalculated).collect(Collectors.toList());
        switch (type) {
            case "register_num":
                value = data.stream().map(StatisticsDaily::getRegisterNum).collect(Collectors.toList());
                break;
            case "login_num":
                value = data.stream().map(StatisticsDaily::getLoginNum).collect(Collectors.toList());
                break;
            case "video_view_num":
                value = data.stream().map(StatisticsDaily::getVideoViewNum).collect(Collectors.toList());
                break;
            case "course_num":
                value = data.stream().map(StatisticsDaily::getCourseNum).collect(Collectors.toList());
                break;
        }

        Map map = new HashMap();
        map.put("value",value);
        map.put("name",name);
        return map;
    }
}
