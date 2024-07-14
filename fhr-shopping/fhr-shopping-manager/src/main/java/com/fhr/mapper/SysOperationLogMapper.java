package com.fhr.mapper;

import com.fhr.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysOperationLogMapper {
    public abstract void insert(SysOperLog sysOperLog);
}