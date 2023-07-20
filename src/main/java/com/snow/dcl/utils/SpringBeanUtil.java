package com.snow.dcl.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * (功能描述)
 * 从ApplicationContext获取Bean
 *
 * @Author:Dcl_Snow
 * @Create: 2023/7/19 17:04
 * @version: 1.0.0
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 注意 bean name默认 = 类名(首字母小写)
     *
     * @param name
     * @return
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        if (applicationContext == null) {
            return new NullPointerException("applicationContext is null");
        }
        return applicationContext.getBean(name);
    }
}
