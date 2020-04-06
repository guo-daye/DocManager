package com.great.demo.mapper;

import com.great.demo.entity.TB_USER;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    TB_USER judgeId(String user_id);

    Integer addUser(TB_USER tb_user);

    TB_USER login(TB_USER tb_user);

    List<TB_USER> findUserList(Map<String,Object> map);

    Integer findUserCount(Map<String,Object> map);

    Integer updateState(Map<String,Object> map);
}
