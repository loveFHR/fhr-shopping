package com.fhr.mapper;

import com.fhr.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author FHR
 * @Create 2024/4/23 16:20
 * @Version 1.0
 */
@Mapper
public interface UserInfoMapper {

    UserInfo SelectByUserName(String username);

    void save(UserInfo userInfo);
}
