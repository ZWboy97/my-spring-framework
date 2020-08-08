package org.simple.spring.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simple.spring.core.annotations.Component;
import org.simple.spring.core.annotations.Controller;
import org.simple.spring.core.annotations.Repository;
import org.simple.spring.core.annotations.Service;
import org.simple.spring.core.utils.ClassUtils;
import org.simple.spring.core.utils.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/2 22:42
 */

/**
 * 基于枚举的单例模式，实现的IOC容器
 * 支持线程安全、阻挡反射的单例模式
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

    /**
     * 容器加载类的时候，关注注解类型目录
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(Controller.class, Component.class, Repository.class, Service.class);
    /**
     * 容器载体，并发性能优秀
     */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap();
    private boolean loaded = false;

    /**
     * 获取IOC容器实例
     *
     * @return
     */
    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    public Map<Class<?>, Object> getBeanMap() {
        return this.beanMap;
    }

    /**
     * 向IOC容器中加载Bean
     *
     * @param packageName
     */
    public synchronized void loadBeans(String packageName) {
        // 判断容器是否会被加载过
        if (isLoaded()) {
            return;
        }
        Set<Class<?>> classSet = ClassUtils.extraPackageClass(packageName);
        if (ValidationUtil.isEmpty(classSet)) {
            log.error("no class in package");
        }
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                if (clazz.isAnnotationPresent(annotation)) {
                    beanMap.put(clazz, ClassUtils.newInstance(clazz, true));
                }
            }
        }
        loaded = true;
    }

    /**
     * 向Bean容器中添加一个Bean
     *
     * @param clazz
     * @param bean
     */
    public Object addBean(Class<?> clazz, Object bean) {
        return this.beanMap.put(clazz, bean);
    }

    /**
     * 从Bean容器中移除一个Bean
     *
     * @param clazz
     */
    public Object removeBean(Class<?> clazz) {
        return this.beanMap.remove(clazz);
    }

    /**
     * 从Bean容器中获取一个Bean
     *
     * @param clazz
     * @return
     */
    public Object getBean(Class<?> clazz) {
        return this.beanMap.get(clazz);
    }

    public Set<Class<?>> getClasses() {
        return this.beanMap.keySet();
    }

    public Set<Object> getBeans() {
        return (HashSet<Object>) this.beanMap.values();
    }

    /**
     * 基于指定注解，获取标注了这些注解的class对象
     * @param annotation
     * @return
     */
    public Set<Class<?>> getClassByAnnotation(Class<? extends Annotation> annotation) {
        // 获取所有的class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            return null;
        }
        // 筛选出指定注解标记的
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            // isAnnotationPresent 判断该类是否有指定注解
            if (clazz.isAnnotationPresent(annotation)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }

    public Set<Class<?>> getClassBySuper(Class<?> superClass) {
        // 获取所有的class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            return null;
        }
        // 筛选出指定注解标记的
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            // isAssignableFrom 判断clazz是否是superClass的子类或子接口
            if (superClass.isAssignableFrom(clazz) && !clazz.equals(superClass)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }


    public boolean isLoaded() {
        return this.loaded;
    }

    private enum ContainerHolder {
        // s
        HOLDER;

        private BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

}
