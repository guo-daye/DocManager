package com.great.demo.mapper;

import com.great.demo.entity.TB_MENU;
import com.great.demo.entity.TB_ROLE;
import com.great.demo.entity.TB_ROLERELATION;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AuthorityMapper {
    TB_ROLERELATION selectAuthority(Integer row_id);

    List<TB_MENU> findParentMenu(Integer parent_id);

    List<TB_MENU> findMenu(HashMap<String, Object> map);

    List<TB_ROLE> findRoleList(HashMap<String, Object> map);

    Integer findRoleCount(HashMap<String, Object> map);

    Integer resetMenuState(HashMap<String, Object> map);

    Integer resetAllMenu(HashMap<String, Object> map);

    Integer addRoleRelation(TB_ROLERELATION tb_rolerelation);

    TB_ROLE findRoleId(String role);
}
