package com.fhr.service;

import com.fhr.model.dto.h5.UserLoginDto;
import com.fhr.model.dto.h5.UserRegisterDto;
import com.fhr.model.vo.h5.UserInfoVo;

/**
 * @Author FHR
 * @Create 2024/4/23 16:18
 * @Version 1.0
 */
public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);

    void logout(String token);
}
