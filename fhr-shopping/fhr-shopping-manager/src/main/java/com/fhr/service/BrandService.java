package com.fhr.service;

import com.fhr.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/7/13 22:44
 * @Version 1.0
 */
public interface BrandService {
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    void save(Brand brand);

    void updateById(Brand brand);

    void deleteById(Long id);

    List<Brand> findAll();
}
