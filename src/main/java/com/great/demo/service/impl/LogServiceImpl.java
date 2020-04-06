package com.great.demo.service.impl;

import com.great.demo.entity.TB_LOG;
import com.great.demo.mapper.LogMapper;
import com.great.demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    private LogMapper logMapper;

    @Override
    @Transactional
    public Integer addLog(TB_LOG tb_log) {
        return logMapper.addLog(tb_log);
    }
}
