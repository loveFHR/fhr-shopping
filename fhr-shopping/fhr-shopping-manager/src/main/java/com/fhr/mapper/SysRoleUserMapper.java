package com.fhr.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/7/13 17:14
 * @Version 1.0
 */
@Mapper
public interface SysRoleUserMapper {
    /**
     * 根据用户id删除角色数据
     * @param userId
     */
    public void deleteByUserId(Long userId);

    /**
     * 分配角色
     * @param userId
     */
    void doAssign(Long roleId,Long userId);

    /**
     * 根据用户id查询用户分配过角色id列表
     * @param userId
     * @return
     */
    List<Long> selectRoleIdByUserId(Long userId);
}
