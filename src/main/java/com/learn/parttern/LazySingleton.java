package com.learn.parttern;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/2 22:10
 */

/**
 * 懒汉单例模式
 * 在使用到该实例的时候，才对类进行实例化
 * <p>
 * 双重检测方式
 *
 * 是无法抵挡反射机制的
 */
public class LazySingleton {

    // 变量需要volitile修饰，
    // 阻止指令重排

    private volatile static LazySingleton instance;

    private LazySingleton() {

    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            // 也许，这时有线程正在实例化，但还没完成
            synchronized (LazySingleton.class) {
                // 保障只被实例化一次, 因为实例化过程包括三个阶段
                if (instance == null) {
                    instance = new LazySingleton();
                    // 1. 分配对象内容空间
                    // 2. 初始化对象
                    // 3. 设置instance指向初始化好的内存空间
                    // 可能回存在重拍，还没初始化就赋值instance，所以需要volatile禁止重排
                }
            }
        }
        return instance;
    }

}
