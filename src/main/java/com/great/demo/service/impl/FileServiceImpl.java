package com.great.demo.service.impl;

import com.great.demo.entity.ResultDate;
import com.great.demo.entity.TB_FILE;
import com.great.demo.mapper.FileMapper;
import com.great.demo.service.FileService;
import com.great.demo.util.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private FileMapper fileMapper;

    @Override
    public Integer addFile(TB_FILE tb_file) {
        return fileMapper.addFile(tb_file);
    }

    @Override
    public ResultDate<TB_FILE> findFileList(Map<String, Object> map) {
        ResultDate<TB_FILE> rd = ApplicationContextHelper.popBean("ResultDate",ResultDate.class);
        rd.setData(fileMapper.findFileList(map));
        rd.setCount(fileMapper.findFileCount(map));
        rd.setMsg("");
        rd.setCode(0);
        return rd;
    }

    @Override
    public TB_FILE findFile(Integer file_id) {
        return fileMapper.findFile(file_id);
    }

    @Override
    public Integer updateState(Map<String, Object> map) {
        return fileMapper.updateState(map);
    }

    @Override
    public Map<String, Object> userFind(Map<String, Object> map) {
        Map<String,Object> returnMap = new HashMap<>();
        List<TB_FILE> fileList = fileMapper.userFindList(map);
        Integer count = fileMapper.userFindCount(map);
        returnMap.put("fileList",fileList);
        returnMap.put("count",count);
        System.out.println(returnMap);
        return returnMap;
    }
}
