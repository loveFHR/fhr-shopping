package com.fhr.service.impl;

import com.fhr.mapper.SysRoleMapper;
import com.fhr.mapper.SysRoleUserMapper;
import com.fhr.model.dto.system.SysRoleDto;
import com.fhr.model.entity.system.SysRole;
import com.fhr.service.SysRoleService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author FHR
 * @Create 2024/4/22 15:44
 * @Version 1.0
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    /**
     * 查询角色列表
     * @param current
     * @param limit
     * @param sysRoleDto
     * @return
     */
    @Override
    public PageInfo<SysRole> findByPage(Integer current, Integer limit, SysRoleDto sysRoleDto) {
        PageHelper.startPage(current,limit);
        //根据条件查询所有数据
        List<SysRole> list =  sysRoleMapper.findByPage(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    /**
     * 添加角色
     * @param sysRole
     */
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    /**
     * 修改角色
     * @param sysRole
     */
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole);
    }

    /**
     * 删除角色
     * @param roleId
     */
    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    /**
     * 查询所有角色
     * @return
     */

    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        //查询所有角色
        List<SysRole> roleList = sysRoleMapper.findAll();
        //分配过的角色列表
        List<Long> roleIds = sysRoleUserMapper.selectRoleIdByUserId(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("allRolesList",roleList);
        map.put("sysUserRoles", roleIds);
        return map;
    }
}
