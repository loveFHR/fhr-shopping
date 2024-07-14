package com.fhr.mapper;

import com.fhr.model.entity.order.OrderLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author FHR
 * @Create 2024/4/24 13:25
 * @Version 1.0
 */
@Mapper
public interface OrderLogMapper {
    void save(OrderLog orderLog);
}
