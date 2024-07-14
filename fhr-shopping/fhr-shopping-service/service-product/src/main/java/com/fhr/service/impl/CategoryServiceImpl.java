package com.fhr.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.fhr.mapper.CategoryMapper;
import com.fhr.model.entity.product.Category;
import com.fhr.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// 接口实现类
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String CATEGORY_ONE = "category:one";
    /**
     * 查询所有一级分类
     * @return
     */
    @Override
    public List<Category> findOneCategory() {
        //查redis
        String categoryOneJSON = redisTemplate.opsForValue().get(CATEGORY_ONE);
        if (!StrUtil.isEmpty(categoryOneJSON)) {
            //json字符串转list
            List<Category> categoryList = JSON.parseArray(categoryOneJSON, Category.class);
            return categoryList;
        }
        //没有就查数据库，并写到redis
        List<Category> categoryList = categoryMapper.findOneCategory();
        redisTemplate.opsForValue().set(CATEGORY_ONE,JSON.toJSONString(categoryList),30, TimeUnit.MINUTES);
        return categoryList;
    }

    /**
     * 查询所有分类，树形封装
     * @return
     */
    @Override
    @Cacheable(value = "category",key = "'all'") //key的值为: category::all
    public List<Category> findCategoryTree() {
        List<Category> categoryList = categoryMapper.findAll();
        //全部一级分类
        List<Category> oneCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == 0).collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(oneCategoryList)) {
            oneCategoryList.forEach(oneCategory -> {
                List<Category> twoCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == oneCategory.getId().longValue()).collect(Collectors.toList());
                oneCategory.setChildren(twoCategoryList);

                if(!CollectionUtils.isEmpty(twoCategoryList)) {
                    twoCategoryList.forEach(twoCategory -> {
                        List<Category> threeCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == twoCategory.getId().longValue()).collect(Collectors.toList());
                        twoCategory.setChildren(threeCategoryList);
                    });
                }
            });
        }
        return oneCategoryList;
    }
}