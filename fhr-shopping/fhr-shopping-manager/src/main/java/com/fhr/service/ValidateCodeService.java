package com.fhr.service;

import com.fhr.model.vo.system.ValidateCodeVo;

/**
 * @Author FHR
 * @Create 2024/4/21 21:39
 * @Version 1.0
 */
public interface ValidateCodeService {
    ValidateCodeVo generateValidateCode();
}
