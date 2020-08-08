package org.simple.spring.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/8 15:58
 */
class BeanContainerTest {

    private static BeanContainer beanContainer;

    // Unit5 的初始化
    @BeforeAll
    static void init() {
        beanContainer = BeanContainer.getInstance();
    }

    @DisplayName("加载业务代码中标记了注解的类")
    @Test
    void loadBeans() {
        beanContainer.loadBeans("com.zwboy");
        Map<Class<?>,Object> map = beanContainer.getBeanMap();
        System.out.println(map);
    }
}