package com.fhr.mapper;

import com.fhr.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectByParentId(Long parentId);
    int countByParentId(Long id);

    List<Category> selectAll();

    /**
     * 批量保存分类数据
     * @param cachedDataList
     */
    void batchInsert(List cachedDataList);
}