package com.fhr.log.annotation;

import com.fhr.log.enums.BusinessType;
import com.fhr.log.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 自定义操作日志记录注解
 * @Author FHR
 * @Create 2024/7/14 14:34
 * @Version 1.0
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
     String title() ;								// 模块名称
     OperatorType operatorType() default OperatorType.MANAGE;	// 操作人类别
     BusinessType businessType() ;     // 业务类型（0其它 1新增 2修改 3删除）
     boolean isSaveRequestData() default true;   // 是否保存请求的参数
     boolean isSaveResponseData() default true;  // 是否保存响应的参数
}
