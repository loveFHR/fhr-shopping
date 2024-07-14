package com.fhr.service;

import com.fhr.model.dto.system.SysRoleDto;
import com.fhr.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @Author FHR
 * @Create 2024/4/22 15:43
 * @Version 1.0
 */
public interface SysRoleService {
    PageInfo<SysRole> findByPage(Integer current, Integer limit, SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);

    Map<String, Object> findAllRoles(Long userId);
}
