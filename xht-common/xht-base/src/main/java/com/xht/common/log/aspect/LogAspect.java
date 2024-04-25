package com.xht.common.log.aspect;

import com.xht.common.log.annotation.XhtLog;
import com.xht.common.log.service.AsyncOperLogService;
import com.xht.common.log.utils.LogUtil;
import com.xht.model.entity.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2023/12/29  17:43
 */

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private AsyncOperLogService asyncOperLogService ;

    @Around(value = "@annotation(xhtLog)") //value  定义切点
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint , XhtLog xhtLog) {

        // 构建前置参数
        SysOperLog sysOperLog = new SysOperLog() ;
        LogUtil.beforeHandleLog(xhtLog , joinPoint , sysOperLog) ;

        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            // 执行业务方法
            LogUtil.afterHandlLog(xhtLog , proceed , sysOperLog , 0 , null) ;
            // 构建响应结果参数
        } catch (Throwable e) {                                 // 代码执行进入到catch中，
            // 业务方法执行产生异常
            e.printStackTrace();                                // 打印异常信息
            LogUtil.afterHandlLog(xhtLog , proceed , sysOperLog , 1 , e.getMessage()) ;
            throw new RuntimeException();
        }

        // 保存日志数据
        asyncOperLogService.saveSysOperLog(sysOperLog);

        // 返回执行结果
        return proceed ;
    }
}
