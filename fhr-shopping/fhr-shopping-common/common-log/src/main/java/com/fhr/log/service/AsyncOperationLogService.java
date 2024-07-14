package com.fhr.log.service;

import com.fhr.model.entity.system.SysOperLog;

public interface AsyncOperationLogService {			// 保存日志数据
     void saveSysOperationLog(SysOperLog sysOperLog) ;
}