package com.xht.common.log.service.impl;

import com.xht.common.log.mapper.SysOperLogMapper;
import com.xht.common.log.service.AsyncOperLogService;
import com.xht.model.entity.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2023/12/29  17:57
 */
@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    //保存日志数据
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }



}
