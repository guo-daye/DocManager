package com.great.demo.service.impl;

import com.great.demo.entity.*;
import com.great.demo.mapper.AuthorityMapper;
import com.great.demo.mapper.LogMapper;
import com.great.demo.mapper.UserMapper;
import com.great.demo.service.UserService;
import com.great.demo.util.ApplicationContextHelper;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "UserService")
@Scope("singleton")
@Aspect
public class UserServiceImpl implements UserService {
    @Resource
    private AuthorityMapper authorityMapper ;
    @Resource
    private UserMapper userMapper;
    @Resource
    private LogMapper logMapper;

    @Override
    public TB_USER judgeId(String user_id) {
        return userMapper.judgeId(user_id);
    }

    @Override
    @Transactional
    public Integer register(TB_USER tb_user) {
        TB_ROLERELATION tb_rolerelation = ApplicationContextHelper.popBean("TB_ROLERELATION",TB_ROLERELATION.class);
        Integer i = userMapper.addUser(tb_user);
        TB_USER tb_user2 = userMapper.judgeId(tb_user.getUser_id());
        TB_ROLE tb_role = authorityMapper.findRoleId("用户");
        tb_rolerelation.setRole_id(tb_role.getRole_id());
        tb_rolerelation.setRow_id(tb_user2.getRow_id());
        return authorityMapper.addRoleRelation(tb_rolerelation);
    }

    @Override
    public TB_ROLERELATION selectAuthority(Integer row_id) {
        return authorityMapper.selectAuthority(row_id);
    }

    @Override
    public TB_USER login(String user_id,String user_pwd) {
        TB_USER tb_user = ApplicationContextHelper.popBean("TB_USER",TB_USER.class);
        tb_user.setUser_id(user_id);
        tb_user.setUser_pwd(user_pwd);
        return userMapper.login(tb_user);
    }

    @Override
    public List<TB_MENU> findMenu(TB_USER tb_user) {
        TB_ROLERELATION tb_rolerelation = authorityMapper.selectAuthority(tb_user.getRow_id());
        List<TB_MENU> menuList = authorityMapper.findParentMenu(0);
        for (int i = 0;i < menuList.size();i++)
        {
            HashMap<String,Object> map = new HashMap<>();
            map.put("role_id",tb_rolerelation.getRole_id());
            map.put("parent_id",menuList.get(i).getMenu_id());
            List<TB_MENU> menuList2 = authorityMapper.findMenu(map);
            for (int j = 0; j < menuList2.size(); j++) {
                if(menuList2.get(j).getState() == 2)
                {
                    menuList2.remove(j);
                }
            }
            if(menuList2.size()==0)
            {
                menuList.remove(menuList.get(i));
            }else
            {
                menuList.get(i).setMenuList(menuList2);
            }
        }
        System.out.println(menuList);
        return menuList;
    }

    @Override
    public HashMap<String, Object> findRoleList(String role, String page, String limit) {
        HashMap<String,Object> map = new HashMap<>();
        if(role!=null&&!"".equals(role.trim()))
        {
            map.put("role",role);
        }
        if(page!=null&&!"".equals(page.trim())&&limit!=null&&!"".equals(limit.trim()))
        {
            Integer page2 = Integer.valueOf(page),limit2 = Integer.valueOf(limit);
            page2 = (page2-1)*limit2;
            map.put("page",page2);
            map.put("limit",limit2);
            List<TB_ROLE> roleList = authorityMapper.findRoleList(map);
            Integer count = authorityMapper.findRoleCount(map);
            map.clear();
            map.put("roleList",roleList);
            map.put("count",count);
        }
        return map;
    }

    @Override
    public List<Map<String,Object>> findMenu(Integer role_id) {
        List<Map<String,Object>> list = new ArrayList<>();
        List<TB_MENU> menuList2 = authorityMapper.findParentMenu(0);
        for (int i = 0;i < menuList2.size();i++)
        {
            HashMap<String,Object> map = new HashMap<>();
            map.put("role_id",role_id);
            map.put("parent_id",menuList2.get(i).getMenu_id());
            List<TB_MENU> menuList3 = authorityMapper.findMenu(map);
            if(menuList3.size()>0)
            {
                menuList2.get(i).setMenuList(menuList3);
            }else
            {
                menuList2.remove(i);
            }
        }
        for (TB_MENU tb_menu : menuList2)
        {
           Map<String,Object> map = new HashMap<>();
           map.put("title",tb_menu.getMenu_name());
           map.put("id",tb_menu.getMenu_id());
           List<Map<String,Object>> list2 = new ArrayList<>();
           for (TB_MENU tb_menu2 : tb_menu.getMenuList())
           {
               Map<String,Object> map2 = new HashMap<>();
               map2.put("title",tb_menu2.getMenu_name());
               map2.put("id",tb_menu2.getMenu_id());
               if(tb_menu2.getState() == 1)
               {
                   map2.put("checked",true);
               }else
               {
                   map2.put("checked",false);
               }
               list2.add(map2);
           }
           map.put("children",list2);
           list.add(map);
        }
        return list;
    }

    @Override
    @Transactional
    public Integer updateRoleMenu(List<TreeData> dataList,Integer role_id) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("role_id",role_id);
        map.put("state",2);
        Integer i = authorityMapper.resetAllMenu(map);
        if(dataList.size()>0)
        {
            List<TB_MENU> menuList = new ArrayList<>();
            for (TreeData treeData : dataList)
            {
                for (TreeData treeData2 : treeData.getChildren())
                {
                    TB_MENU tb_menu = ApplicationContextHelper.popBean("TB_MENU",TB_MENU.class);
                    tb_menu.setMenu_id(treeData2.getId());
                    System.out.println("tb_menu="+tb_menu);
                    menuList.add(tb_menu);
                }
            }
            map.put("state",1);
            map.put("list",menuList);
            Integer j = authorityMapper.resetMenuState(map);
            if(j == menuList.size())
            {
                return j;
            }
        }else
        {
            return i;
        }
        return null;
    }

    @Override
    public ResultDate<TB_USER> findUserList(Map<String, Object> map) {
        ResultDate<TB_USER> resultDate = ApplicationContextHelper.popBean("ResultDate",ResultDate.class);
        resultDate.setData(userMapper.findUserList(map));
        resultDate.setCount(userMapper.findUserCount(map));
        resultDate.setMsg("");
        resultDate.setCode(0);
        return resultDate;
    }

    @Override
    public Integer updateState(Map<String, Object> map) {
        return userMapper.updateState(map);
    }
}
