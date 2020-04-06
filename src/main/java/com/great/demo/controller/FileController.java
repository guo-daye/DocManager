package com.great.demo.controller;

import com.great.demo.annotation.LogAnnotation;
import com.great.demo.entity.ResultDate;
import com.great.demo.entity.TB_FILE;
import com.great.demo.entity.TB_USER;
import com.great.demo.service.impl.FileServiceImpl;
import com.great.demo.util.ApplicationContextHelper;
import com.great.demo.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileServiceImpl fileService;

    @RequestMapping(value = "/upload",produces = {"pplication/xml;charset=utf-8"})
    @LogAnnotation(operationType = "添加",operationName = "上传文件")
    public void fileUpload(String integral, HttpSession session, @RequestParam(value = "file") MultipartFile file, HttpServletResponse response) throws IOException {
        System.out.println("===============用户上传文件================");
        TB_USER tb_user = (TB_USER)session.getAttribute("tb_user");
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        TB_FILE tb_file = ApplicationContextHelper.popBean("TB_FILE",TB_FILE.class);
        tb_file.setFile_url("D:/IDEA/DocumentManagementSystem/src/main/webapp/file/"+file.getOriginalFilename());
        tb_file.setFile_title(file.getOriginalFilename());
        tb_file.setIntegral(Integer.valueOf(integral));
        tb_file.setUser_id(tb_user.getUser_id());
        tb_file.setType(type);
        Integer i = fileService.addFile(tb_file);
        if(i>0) {
            file.transferTo(new File("D:/IDEA/DocumentManagementSystem/src/main/webapp/file/"+file.getOriginalFilename()));
            File file2 = new File("D:/IDEA/DocumentManagementSystem/src/main/webapp/file/"+file.getOriginalFilename());
            if(file2.exists())
            {
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().write("{\"code\":0, \"msg\":\"\", \"data\":{}}");
                response.getWriter().flush();
                response.getWriter().close();
            }
        }
    }

    @RequestMapping("/findFileList")
    public void findFileByPage(@RequestParam Map<String,Object> param, HttpServletResponse response) throws IOException{
        System.out.println("===============管理员搜索文件================");
        Map<String,Object> map = new HashMap<>();
        if(param.containsKey("page")&&param.containsKey("limit"))
        {
            Integer limit = Integer.valueOf(param.get("limit").toString());
            Integer page = Integer.valueOf(param.get("page").toString());
            map.put("page",(page-1)*limit);
            map.put("limit",limit);
        }
        if(param.containsKey("file_title"))
        {
            map.put("file_title",param.get("file_title"));
        }
        if(param.containsKey("user_id"))
        {
            map.put("user-id",param.get("user_id"));
        }
        if(param.containsKey("type")&&!"0".equals(param.get("type"))&&!"null".equals(param.get("type")))
        {
            System.out.println("type="+param.get("type"));
            map.put("type",param.get("type"));
        }
        if(param.containsKey("period")&&!"".equals(param.get("period")))
        {
            String start = param.get("period").toString().substring(0,param.get("period").toString().indexOf("~")).trim();
            String end = param.get("period").toString().substring(param.get("period").toString().indexOf("~")+1).trim();
            map.put("start",start);
            map.put("end",end);
        }
        ResultDate<TB_FILE> rd = fileService.findFileList(map);
        ResponseUtils.outJson(response,rd);
    }

    @RequestMapping("/download")
    @LogAnnotation(operationName = "文件下载",operationType = "IO流")
    public void download(String file_id, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("===============下载文件================");
        TB_FILE tb_file = fileService.findFile(Integer.valueOf(file_id));
        File file = new File(tb_file.getFile_url());
        if(file.exists())
        {
            String filename = tb_file.getFile_title();
            filename = URLEncoder.encode(filename,"UTF-8");
            response.setHeader("content-disposition","attachment;filename="+filename);
            response.setContentType("multipart/form-data");
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            ServletOutputStream os = response.getOutputStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf))!=-1) {
                os.write(buf,0,len);
            }
            os.close();
        }
    }

    @RequestMapping("/checkFile")
    @ResponseBody
    @LogAnnotation(operationType = "审核文件",operationName = "审核通过")
    public String checkFile(@RequestParam Map<String,Object> map) {
        System.out.println("===============管理员审核文件================");
        Integer i = fileService.updateState(map);
        if(i > 0)
        {
            return "success";
        }
        return "error";
    }

    @RequestMapping("/fileList")
    @ResponseBody
    public Map<String,Object> fileList(String file_title,Integer page,Integer limit){
        System.out.println("===============用户搜索文件================");
        page = (page-1)*limit;
        Map<String,Object> map = new HashMap<>();
        if(file_title != null &&!"".equals( file_title.trim())) {
            map.put("file_title",file_title);
        }
        map.put("page",page);
        map.put("limit",limit);
        return fileService.userFind(map);
    }
}

