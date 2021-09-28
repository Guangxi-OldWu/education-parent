package com.wuzhangze.statistics.schedule;

import com.wuzhangze.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author OldWu
 * @date 2021/9/14 19:25
 */
@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    public String getDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR,day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    // 每天凌晨一点，统计一次昨天的数据
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateStatistics() {
        statisticsDailyService.getRegistryCountByDay(getDate(-1));
    }


}
