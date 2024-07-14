package com.fhr.service.impl;

import com.fhr.log.service.AsyncOperationLogService;
import com.fhr.mapper.SysOperationLogMapper;
import com.fhr.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 实现common-log包下定义的记录日志的接口
 */
@Service
public class AsyncOperLogServiceImpl implements AsyncOperationLogService {

    @Autowired
    private SysOperationLogMapper sysOperationLogMapper;

    @Async      // 异步执行保存日志操作
    @Override
    public void saveSysOperationLog(SysOperLog sysOperLog) {
        sysOperationLogMapper.insert(sysOperLog);
    }
    
}