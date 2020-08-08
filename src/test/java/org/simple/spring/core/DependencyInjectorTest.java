package org.simple.spring.core;

import com.zwboy.controller.FirstController;
import com.zwboy.service.FirstService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/8 17:47
 */
class DependencyInjectorTest {

    @Test
    void doIoc() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.zwboy");
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.doIoc();
        FirstController firstController = (FirstController) beanContainer.getBean(FirstController.class);
        FirstService firstService = firstController.getFirstService();
        System.out.println(firstService);
    }
}