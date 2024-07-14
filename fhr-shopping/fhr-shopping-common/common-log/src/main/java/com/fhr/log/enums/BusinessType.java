package com.fhr.log.enums;

import lombok.Getter;

/**
 * @Author FHR
 * @Create 2024/7/14 15:15
 * @Version 1.0
 */
@Getter
public enum BusinessType {
    /**
     * 其他
     */
    OTHER(0),
    /**
     * 新增
     */
    INSERT(1),
    /**
     * 修改
     */
    UPDATE(2),
    /**
     * 删除
     */
    DELETE(3),
    ;
    private int businessType;
    private BusinessType(int businessType){
        this.businessType = businessType;
    }
}
