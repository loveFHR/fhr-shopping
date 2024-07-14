package com.fhr.log.aspect;

import com.fhr.log.annotation.Log;
import com.fhr.log.service.AsyncOperationLogService;
import com.fhr.log.utils.LogUtil;
import com.fhr.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author FHR
 * @Create 2024/7/14 14:39
 * @Version 1.0
 */

@Aspect
@Component
public class LogAspect {

    @Autowired
    private AsyncOperationLogService asyncOperationLogService;
    //环绕通知
    @Around(value = "@annotation(sysLog)")
    public  Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog) {
        //业务调用之前封装数据
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog,joinPoint,sysOperLog);
        Object proceed = null;

        try {
            //执行业务方法
             proceed = joinPoint.proceed();

             //调用业务方法之后封装数据（成功的）
            LogUtil.afterHandleLog(sysLog,proceed,sysOperLog,0,null);
        } catch (Throwable e) {
            e.printStackTrace();
            //调用业务方法之后封装数据（异常的）
            LogUtil.afterHandleLog(sysLog,proceed,sysOperLog,1,e.getMessage());
            throw new RuntimeException(); //一定要抛出异常，否则可能事务失效
        }
        //调用service方法把日志信息记录到数据库
        asyncOperationLogService.saveSysOperationLog(sysOperLog);
        return  proceed;
    }

}
