package com.fhr.task;

import cn.hutool.core.date.DateUtil;
import com.fhr.mapper.OrderInfoMapper;
import com.fhr.mapper.OrderStatisticsMapper;
import com.fhr.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author FHR
 * @Create 2024/7/14 13:30
 * @Version 1.0
 */
@Component
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;


    //每天凌晨两点，查询前一天的统计数据，把统计之后数据添加到统计结果表里面
    @Scheduled(cron = "0 0 2 * * ?")
    public void orderTotalAmountStatistics() {
        //获取前一天的日期
        String createDate = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
        //根据前一天的日期进行统计功能，（分组操作）
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createDate);
        //把统计之后的数据，添加到统计结果表里面
        if (orderStatistics != null) {
            orderStatisticsMapper.insert(orderStatistics);
        }
    }
}
