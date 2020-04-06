package com.great.demo.mapper;

import com.great.demo.entity.TB_LOG;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    Integer addLog(TB_LOG tb_log);
}
