package com.great.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        System.out.println("==>注入"+context+"到ApplicationContextHelper<==");
        ac = context;
    }

    public static <T> T popBean(Class<T> clazz) {
        if (ac == null) {
            System.out.println("[error]==>ApplicationContext未注入，请检查");
            return null;
        }
        return ac.getBean(clazz);
    }

    public static <T> T popBean(String name, Class<T> clazz) {
        if (ac == null) {
            System.out.println("[error]==>ApplicationContext未注入，请检查");
            return null;
        }
        return ac.getBean(name, clazz);
    }
}
