package com.fhr.mapper;

import com.fhr.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/4/24 13:24
 * @Version 1.0
 */
@Mapper
public interface OrderItemMapper {
    void save(OrderItem orderItem);

    /**
     * 根据订单id查询订单项
     * @param id
     * @return
     */
    List<OrderItem> findByOrderId(Long id);
}
