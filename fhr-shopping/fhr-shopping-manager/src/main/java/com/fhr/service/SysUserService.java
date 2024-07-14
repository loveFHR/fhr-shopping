package com.fhr.service;

import com.fhr.model.dto.system.AssignRoleDto;
import com.fhr.model.dto.system.LoginDto;
import com.fhr.model.dto.system.SysUserDto;
import com.fhr.model.entity.system.SysUser;
import com.fhr.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;

public interface SysUserService {

    /**
     * 根据用户名查询用户数据
     * @return
     */
    public abstract LoginVo login(LoginDto loginDto) ;

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);

    void doAssign(AssignRoleDto assignRoleDto);
}