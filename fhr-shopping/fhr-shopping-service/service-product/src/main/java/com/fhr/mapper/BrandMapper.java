package com.fhr.mapper;

import com.fhr.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/4/22 22:51
 * @Version 1.0
 */
@Mapper
public interface BrandMapper {
    List<Brand> findAll();
}
