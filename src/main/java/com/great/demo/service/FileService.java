package com.great.demo.service;


import com.great.demo.entity.ResultDate;
import com.great.demo.entity.TB_FILE;

import java.util.Map;

public interface FileService {
    Integer addFile(TB_FILE tb_file);

    ResultDate<TB_FILE> findFileList(Map<String, Object> map);

    TB_FILE findFile(Integer file_id);

    Integer updateState(Map<String, Object> map);

    Map<String,Object> userFind(Map<String,Object> map);
}
