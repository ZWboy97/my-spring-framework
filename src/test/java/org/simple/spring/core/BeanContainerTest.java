package org.simple.spring.core;

import com.zwboy.service.FirstService;
import org.junit.jupiter.api.*;
import org.simple.spring.core.annotations.Controller;
import org.simple.spring.core.annotations.Service;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/8 15:58
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeanContainerTest {

    private static BeanContainer beanContainer;

    // Unit5 的初始化
    @BeforeAll
    static void init() {
        beanContainer = BeanContainer.getInstance();
    }

    @Order(1)
    @DisplayName("加载业务代码中标记了注解的类")
    @Test
    void loadBeans() {
        beanContainer.loadBeans("com.zwboy");
        Map<Class<?>,Object> map = beanContainer.getBeanMap();
        System.out.println("加载所有的Beans"+map);
    }

    @Test
    void getClassByAnnotation() {
        Set<Class<?>> result = beanContainer.getClassByAnnotation(Controller.class);
        System.out.println("基于注解获取"+result);
    }

    @Test
    void getClassBySuper() {
        Set<Class<?>> result = beanContainer.getClassBySuper(FirstService.class);
        System.out.println("基于父类获取"+result);
    }
}