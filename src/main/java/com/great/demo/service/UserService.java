package com.great.demo.service;

import com.great.demo.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {
    TB_USER judgeId(String user_id);

    Integer register(TB_USER tb_user);

    TB_ROLERELATION selectAuthority(Integer row_id);

    TB_USER login(String user_id, String user_pwd);

    List<TB_MENU> findMenu(TB_USER tb_user);

    HashMap<String,Object> findRoleList(String role, String page, String limit);

    List<Map<String,Object>> findMenu(Integer role_id);

    Integer updateRoleMenu(List<TreeData> dataList, Integer role_id);

    ResultDate<TB_USER> findUserList(Map<String,Object> map);

    Integer updateState(Map<String,Object> map);
}
