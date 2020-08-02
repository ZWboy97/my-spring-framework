package org.simple.spring.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/2 21:46
 */
class ClassUtilsTest {

    @DisplayName("提取目标类方法")
    @Test
    void extraPackageClass() {
        Set<Class<?>> classSet =
                ClassUtils.extraPackageClass("org.simple.spring");
        System.out.println(classSet);
        Assertions.assertEquals(3,classSet.size());

    }
}