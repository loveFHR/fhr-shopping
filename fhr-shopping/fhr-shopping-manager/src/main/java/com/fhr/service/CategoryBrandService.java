package com.fhr.service;

import com.fhr.model.dto.product.CategoryBrandDto;
import com.fhr.model.entity.product.Brand;
import com.fhr.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/7/13 23:11
 * @Version 1.0
 */
public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void updateById(CategoryBrand categoryBrand);

    void deleteById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
