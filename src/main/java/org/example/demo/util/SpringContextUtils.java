package org.example.demo.util;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 类描述：Spring容器工具类（获取Bean实例）
 *
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 02:17
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
    /**
     * 静态保存ApplicationContext引用
     * -- GETTER --
     *  获取ApplicationContext

     */
    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextUtils.applicationContext = context;
    }

    /**
     * 根据名称获取Bean
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    /**
     * 根据类型获取Bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    /**
     * 根据名称和类型获取Bean
     */
    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return applicationContext.getBean(beanName, beanClass);
    }
}