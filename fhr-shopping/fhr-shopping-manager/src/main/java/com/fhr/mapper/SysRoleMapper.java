package com.fhr.mapper;

import com.fhr.model.dto.system.SysRoleDto;
import com.fhr.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/4/22 15:44
 * @Version 1.0
 */
@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);

    List<SysRole> findAll();
}
