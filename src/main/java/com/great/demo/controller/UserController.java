package com.great.demo.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.great.demo.annotation.LogAnnotation;
import com.great.demo.entity.*;
import com.great.demo.service.impl.UserServiceImpl;
import com.great.demo.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/judgeId")
    public String judgeId(String user_id) {
        TB_USER tb_user = userService.judgeId(user_id);
        if(tb_user!=null)
        {
            return "用户名已存在";
        }else
        {
            return "用户名可以使用";
        }
    }

    @RequestMapping("/judgeUser")
    public Object judgeUser(HttpSession session)  {
        TB_USER tb_user = (TB_USER)session.getAttribute("tb_user");
        if(tb_user!=null)
        {
            return "no";
        }else
        {
            return "login";
        }
    }

    @RequestMapping("/addUser")
    @LogAnnotation(operationName = "注册",operationType = "添加")
    public String addUser(@Validated TB_USER tb_user, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
        {
            StringBuilder sb = new StringBuilder();
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList)
            {
                sb.append(error.getDefaultMessage()).append("|");
            }
            return sb.toString();
        }else
        {
            tb_user.setIntegral(0);
            Integer i = userService.register(tb_user);
            if(i>0)
            {
                return "注册成功，即将返回登陆界面";
            }
        }
        return "注册失败，请检查代码";
    }

    @RequestMapping("/login")
    @LogAnnotation(operationName = "登陆",operationType = "查询")
    public String login(@RequestParam Map<String,Object> param, HttpSession session) {
        if(param.get("code").toString().equalsIgnoreCase((String)session.getAttribute("verifyCode")))
        {
            TB_USER tb_user = userService.login(param.get("user_id").toString(),param.get("user_pwd").toString());
            session.setAttribute("tb_user",tb_user);
            if(tb_user!=null)
            {
                TB_ROLERELATION tb_rolerelation = userService.selectAuthority(tb_user.getRow_id());
                if(tb_user.getState()==1)
                {
                    Gson gson = new Gson();
                    return gson.toJson(tb_rolerelation);
                }else
                {
                    return "您的账号已被封禁";
                }
            }else
            {
                return "账号或密码错误";
            }
        }else
        {
            return "验证码错误";
        }
    }

    @RequestMapping("/findMenu")
    public String findMenu(HttpSession session)  {
        TB_USER tb_user = (TB_USER)session.getAttribute("tb_user");
        if(tb_user!=null)
        {
            List<TB_MENU> menuList = userService.findMenu(tb_user);
            Gson gson = new Gson();
            return  gson.toJson(menuList);
        }else
        {
            return "error";
        }
    }

    @RequestMapping("/exit")
    @LogAnnotation(operationName = "退出",operationType = "查询")
    public String exit(HttpSession session)  {
        session.setAttribute("tb_user",null);
        TB_USER tb_user = (TB_USER)session.getAttribute("tb_user");
        if(null == tb_user)
        {
            return "退出成功";
        }else
        {
            return "用户未登录";
        }
    }

    @RequestMapping("/findRoleList")
    public Map findRoleList(@RequestParam Map<String,Object> param) {
        HashMap<String,Object> map = userService.findRoleList(param.get("role").toString(),param.get("page").toString(),param.get("limit").toString());
        if(!map.isEmpty())
        {
            return map;
        }else
        {
            return null;
        }
    }

    @RequestMapping("/findRoleMenu")
    public List findRoleMenu(String role_id)  {
        List<Map<String,Object>> list= userService.findMenu(Integer.valueOf(role_id));
        return list;
    }

    @RequestMapping("/updateRoleMenu")
    @LogAnnotation(operationName = "修改菜单",operationType = "更新")
    public String updateRoleMenu(String treeDate,String role_id)  {
        Gson gson = new Gson();
        List<TreeData> dataList = gson.fromJson(treeDate,new TypeToken<List<TreeData>>() {}.getType());
        System.out.println(dataList);
        Integer i = userService.updateRoleMenu(dataList,Integer.valueOf(role_id));
        if(i!=null)
        {
            return "修改菜单成功，将在该角色下次登陆后生效";
        }else
        {
            return "修改失败，请检查代码";
        }
    }

    @RequestMapping("/findUserList")
    public void findUserList(@RequestParam Map<String,Object> param, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("====================搜索用户列表=====================");
        Map<String,Object> map = new HashMap<>();
        if(param.containsKey("page")&&param.containsKey("limit"))
        {
            Integer limit = Integer.valueOf(param.get("limit").toString());
            Integer page = Integer.valueOf(param.get("page").toString());
            map.put("page",(page-1)*limit);
            map.put("limit",limit);
        }
        if(param.containsKey("user_id")){
            map.put("user_id",param.get("user_id"));
        }
        if(param.containsKey("state")&&Integer.valueOf(param.get("state").toString())!=0){
            map.put("state",param.get("state"));
        }
        if(param.containsKey("period")&&!"".equals(param.get("period")))
        {
            String start = param.get("period").toString().substring(0,param.get("period").toString().indexOf("~")).trim();
            String end = param.get("period").toString().substring(param.get("period").toString().indexOf("~")+1).trim();
            map.put("start",start);
            map.put("end",end);
        }
        ResponseUtils.outJson(response,userService.findUserList(map));
    }

    @RequestMapping("/updateState")
    @ResponseBody
    public String updateState(@RequestParam Map<String,Object> param){
        System.out.println("=======================修改用户状态========================");
        Integer i = userService.updateState(param);
        return i > 0 ? "success" : "error";
    }
}
