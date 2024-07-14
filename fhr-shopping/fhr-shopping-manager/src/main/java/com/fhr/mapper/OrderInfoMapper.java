package com.fhr.mapper;

import com.fhr.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {

    /** 查询指定日期产生的订单数据
     *
     * @param creatTime
     * @return
     */
     OrderStatistics selectOrderStatistics(String creatTime);

}