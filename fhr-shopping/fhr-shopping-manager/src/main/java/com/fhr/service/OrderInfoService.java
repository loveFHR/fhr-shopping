package com.fhr.service;

import com.fhr.model.dto.order.OrderStatisticsDto;
import com.fhr.model.vo.order.OrderStatisticsVo;

/**
 * @Author FHR
 * @Create 2024/7/14 13:57
 * @Version 1.0
 */
public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
