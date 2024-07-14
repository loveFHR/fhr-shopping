package com.fhr.service;

import com.fhr.model.dto.system.AssginMenuDto;

import java.util.Map;

/**
 * @Author FHR
 * @Create 2024/7/13 20:32
 * @Version 1.0
 */
public interface SysRoleMenuService {

    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginMenuDto);
}
