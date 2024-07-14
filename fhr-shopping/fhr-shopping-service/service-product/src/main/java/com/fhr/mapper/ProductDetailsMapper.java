package com.fhr.mapper;

import com.fhr.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author FHR
 * @Create 2024/4/22 23:10
 * @Version 1.0
 */
@Mapper
public interface ProductDetailsMapper {
    ProductDetails getByProductId(Long productId);
}
