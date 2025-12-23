package org.example.demo.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：Bean对象拷贝工具类
 *
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 02:14
 */
public class BeanCopyUtils {

    /**
     * 单个对象拷贝
     */
    public static <T> T copyBean(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        T target = null;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException("Bean拷贝失败", e);
        }
        return target;
    }

    /**
     * 集合对象拷贝
     */
    public static <S, T> List<T> copyBeanList(List<S> sourceList, Class<T> targetClass) {
        List<T> targetList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sourceList)) {
            for (S source : sourceList) {
                T target = copyBean(source, targetClass);
                targetList.add(target);
            }
        }
        return targetList;
    }
}