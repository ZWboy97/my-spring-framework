package com.learn.parttern;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/2 22:33
 */

/**
 * 装备了枚举的饿汉模式
 * 可以抵挡住反射的进攻
 */
public class EnumStarvingSingleton {

    private EnumStarvingSingleton() {

    }

    public static EnumStarvingSingleton getInstance() {
        return ContainerHolder.HOLDER.instance;
    }


    private enum ContainerHolder {
        // holder
        HOLDER;

        private EnumStarvingSingleton instance;

        ContainerHolder() {
            // 在枚举类加载初始化过程中，完成实例化
            instance = new EnumStarvingSingleton();
        }
    }

}
