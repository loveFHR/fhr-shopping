package com.fhr.exception;

import com.fhr.model.vo.common.ResultCodeEnum;
import lombok.Data;

/**
 * @Author FHR
 * @Create 2024/4/21 20:56
 * @Version 1.0
 */
@Data
public class FHRException extends RuntimeException{
    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;

    public FHRException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}
