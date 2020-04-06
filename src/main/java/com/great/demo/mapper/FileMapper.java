package com.great.demo.mapper;

import com.great.demo.entity.TB_FILE;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper {
    Integer addFile(TB_FILE tb_file);

    List<TB_FILE> findFileList(Map<String, Object> map);

    Integer findFileCount(Map<String, Object> map);

    TB_FILE findFile(Integer file_id);

    Integer updateState(Map<String, Object> map);

    List<TB_FILE> userFindList(Map<String,Object> map);

    Integer userFindCount(Map<String,Object> map);
}
