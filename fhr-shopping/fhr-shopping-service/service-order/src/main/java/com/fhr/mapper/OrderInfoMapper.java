package com.fhr.mapper;

import com.fhr.model.entity.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/4/24 13:24
 * @Version 1.0
 */
@Mapper
public interface OrderInfoMapper {
    void save(OrderInfo orderInfo);

    OrderInfo getById(Long orderId);

    /**
     * 根据用户id和状态查询
     * @param userId
     * @param orderStatus
     * @return
     */
    List<OrderInfo> findUserPage(Long userId, Integer orderStatus);

    OrderInfo getByOrderNo(String orderNo);

    void updateById(OrderInfo orderInfo);
}
