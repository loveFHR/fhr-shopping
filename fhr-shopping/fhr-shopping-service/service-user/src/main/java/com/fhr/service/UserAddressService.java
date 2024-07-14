package com.fhr.service;

import com.fhr.model.entity.user.UserAddress;

import java.util.List;

public interface UserAddressService {

    List<UserAddress> findUserAddressList();

    UserAddress getById(Long id);
}