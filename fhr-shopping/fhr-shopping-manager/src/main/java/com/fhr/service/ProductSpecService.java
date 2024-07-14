package com.fhr.service;

import com.fhr.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/7/14 0:13
 * @Version 1.0
 */
public interface ProductSpecService {
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);

    void save(ProductSpec productSpec);

    void updateById(ProductSpec productSpec);

    void deleteById(Long id);

    List<ProductSpec> findAll();

}
