package com.fhr.exception;

import com.fhr.model.vo.common.Result;
import com.fhr.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author FHR
 * @Create 2024/4/21 20:52
 * @Version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.build(null , ResultCodeEnum.SYSTEM_ERROR);
    }

    //自定义异常处理
    @ExceptionHandler(FHRException.class)
    @ResponseBody
    public Result error(FHRException exception){
        return Result.build(null , exception.getResultCodeEnum());
    }
}
