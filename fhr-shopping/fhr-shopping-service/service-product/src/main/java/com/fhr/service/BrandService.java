package com.fhr.service;

import com.fhr.model.entity.product.Brand;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/4/22 22:50
 * @Version 1.0
 */
public interface BrandService {
    List<Brand> findAll();
}
