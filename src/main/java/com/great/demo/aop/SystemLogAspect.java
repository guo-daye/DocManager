package com.great.demo.aop;

import com.great.demo.annotation.LogAnnotation;
import com.great.demo.entity.TB_LOG;
import com.great.demo.entity.TB_USER;
import com.great.demo.service.impl.LogServiceImpl;
import com.great.demo.util.ApplicationContextHelper;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;


@Component
@Aspect
public class SystemLogAspect {
    @Autowired
    private LogServiceImpl logService;// 日志Service
    @Autowired
    private HttpSession session;

    @Pointcut("within(com.great.demo.controller.*)&&@annotation(com.great.demo.annotation.LogAnnotation)")
    public void logPointCut() { }

    @After("logPointCut()")
    public void aroundAdvice(JoinPoint joinPoint) {
        TB_USER tb_user = (TB_USER)session.getAttribute("tb_user");
        if(tb_user == null)
        {
            return;
        }
        TB_LOG tb_log = ApplicationContextHelper.popBean("TB_LOG", TB_LOG.class);
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operationType = "";
            String operationName = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operationType = method.getAnnotation(LogAnnotation.class).operationType();
                        operationName = method.getAnnotation(LogAnnotation.class).operationName();
                        break;
                    }
                }
            }
            //*========控制台输出=========*//
            System.out.println("=====controller后置通知开始=====");
            System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);
            System.out.println("方法描述:" + operationName);
            System.out.println("请求人:" + tb_user.getUser_id());
            //*========数据库日志=========*//
            tb_log.setUser_id(tb_user.getUser_id());
            tb_log.setOperationType((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);
            tb_log.setOperationName(operationName);
            tb_log.setResult("成功");
        }  catch (Exception e) {
            //记录本地异常日志
            System.out.println("[error]:后置通知异常==>异常信息:"+e.getMessage());
        }finally {
            //保存数据库
            logService.addLog(tb_log);
            System.out.println("=====controller后置通知结束=====");
        }
    }
}
