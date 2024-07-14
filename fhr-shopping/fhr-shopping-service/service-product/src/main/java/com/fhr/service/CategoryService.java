package com.fhr.service;

import com.fhr.model.entity.product.Category;

import java.util.List;

// 业务接口
public interface CategoryService {

    List<Category> findOneCategory();

    List<Category> findCategoryTree();
}