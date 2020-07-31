package org.simple.spring.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: LiJiaChang
 * @Date: 2020/7/31 23:03
 */
@Target(ElementType.TYPE) // 类注解
@Retention(RetentionPolicy.RUNTIME) // 运行时
public @interface Controller {
}
