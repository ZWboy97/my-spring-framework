package com.zwboy.learn;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: LiJiaChang
 * @Date: 2020/7/22 21:55
 */
public class ReflectLearn {


    ReflectLearn(){
        System.out.println("默认");
    }

    public ReflectLearn(String args){
        System.out.println("带参数的public构造函数");
    }

    protected ReflectLearn(String args,int index){
        System.out.println("Protected的构造函数");
    }

    private ReflectLearn(int index){
        System.out.println("private 的构造函数");
    }

    public void say(){
        System.out.println("hello world");
    }

    protected void hh(String arg){
        System.out.println(" This is secret "+arg);
    }

    private void good(int index){
        System.out.println("private void invoked");
    }

    public String name;

    private int index;

    protected int hh;

    @Override
    public String toString() {
        return "ReflectLearn{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", hh=" + hh +
                '}';
    }

    public static void main(String [] args) throws  Exception{
        String str = "sfsadf";
        // 第一种获取class对象
        System.out.println(str.getClass().getName());
        // 第二种，需要导入目标类
        System.out.println(String.class.getName());
        // 第三种，基于反射，不需要导入目标类，直接基于去1
        System.out.println(Class.forName("java.lang.String").getName());
        // 获取到的Class 对象都是同一个class，即JVM加载到内存中的唯一一格class

        // 反射获取类的构造方法
        Class targetClass = Class.forName("com.zwboy.learn.ReflectLearn");
        // 获取所有public构造函数
        Constructor<ReflectLearn> [] publicConstructors = targetClass.getConstructors(); // 获取所有public构造方法
        for (Constructor constructor :
                publicConstructors) {
            System.out.println(constructor.toString());
        }
        // 获取所有构造函数
        Constructor<ReflectLearn> [] allConstructors = targetClass.getDeclaredConstructors(); // 获取所有public构造方法
        for (Constructor constructor :
                allConstructors) {
            System.out.println(constructor.toString());
        }
        // 获取单个public构造函数
        Constructor<ReflectLearn> constructor = targetClass.getConstructor(String.class);
        System.out.println(constructor.toString());
        // 获取单个任意构造函数
        Constructor<ReflectLearn> privateConstructor = targetClass.getDeclaredConstructor(int.class);
        System.out.println(privateConstructor.toString());
        //privateConstructor.setAccessible(true); // 必须，然后才能够通过反射调用私有方法
        ReflectLearn reflectLearn = privateConstructor.newInstance(2);
        reflectLearn.say();
        System.out.println(privateConstructor.getModifiers());

        /**
         * 反射获取类的成员变量
         */

        Field[] fields = targetClass.getFields(); // 获取所public字段，以及继承的字段
        for (Field field: fields) {
            System.out.println(field.getName());
        }
        fields = targetClass.getDeclaredFields(); // 获取当前类所有字段，不包括继承字段
        for (Field field: fields) {
            System.out.println(field.getName());
        }
        ReflectLearn ref = (ReflectLearn) targetClass.getDeclaredConstructor().newInstance();
        Field nameField = targetClass.getField("name");
        nameField.set(ref,"哈哈哈");
        System.out.println(ref.name+nameField.get(ref));
        Field indexField = targetClass.getDeclaredField("index");
        indexField.set(ref,300);
        System.out.println(indexField.get(ref));

        /**
         * 反射访问方法
         */
        Method [] methods = targetClass.getMethods();// 获取所有public方法，包含父类的方法
        for (Method method: methods) {
            System.out.println(method.toString());
        }
        methods = targetClass.getDeclaredMethods(); // 获取所有方法，包括父类
        for (Method method:methods) {
            System.out.println(method.toString());
        }
        Method method = targetClass.getDeclaredMethod("good", int.class);
        method.setAccessible(true);
        method.invoke(ref,12);



    }



}
