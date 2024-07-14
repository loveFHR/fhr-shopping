package com.fhr.service.impl;

import com.fhr.mapper.UserAddressMapper;
import com.fhr.model.entity.user.UserAddress;
import com.fhr.service.UserAddressService;
import com.fhr.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserAddressServiceImpl implements UserAddressService {

   @Autowired
   private UserAddressMapper userAddressMapper;

   @Override
   public List<UserAddress> findUserAddressList() {
      Long userId = AuthContextUtil.getUserInfo().getId();
      return userAddressMapper.findByUserId(userId);
   }

    @Override
    public UserAddress getById(Long id) {
        return userAddressMapper.getById(id);
    }
}