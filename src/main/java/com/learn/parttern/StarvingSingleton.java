package com.learn.parttern;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/2 22:10
 */

/**
 * 饿汉单例模式
 * 在类初始化的时候，就实例化
 * 是线程安全的
 * <p>
 * 无法抵挡反射机制的
 */
public class StarvingSingleton {

    private static final StarvingSingleton singleton = new StarvingSingleton();

    private StarvingSingleton() {
    }

    public static StarvingSingleton getInstance() {
        return singleton;
    }

}
