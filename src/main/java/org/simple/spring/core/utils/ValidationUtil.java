package org.simple.spring.core.utils;

import java.util.Collection;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/2 23:09
 */
public class ValidationUtil {


    public static boolean isEmpty(Collection<?> obj) {
        return obj == null && obj.isEmpty();
    }

    public static boolean isEmpty(String obj) {
        return obj == null || "".equals(obj);
    }

    public static <T> boolean isEmpty(T[] objs) {
        return objs.length == 0;
    }

}
