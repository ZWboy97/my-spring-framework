package org.simple.spring.core;

import org.simple.spring.core.annotations.Autowired;
import org.simple.spring.core.utils.ClassUtils;
import org.simple.spring.core.utils.ValidationUtil;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * 依赖注入
 *
 * @Author: LiJiaChang
 * @Date: 2020/8/8 16:52
 */

public class DependencyInjector {

    /**
     * Bean容器
     */
    private BeanContainer beanContainer;

    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * 实现对加载到BeanContainer中的Bean进行依赖注入
     */
    public void doIoc() {
        // 获取Bean容器中的所有Class对象
        Set<Class<?>> classSet = beanContainer.getClasses();
        if (ValidationUtil.isEmpty(classSet)) {
            return;
        }
        for (Class<?> clazz : classSet) {
            // 遍历每个Class对象的所有成员变量
            Field[] fields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(fields)) {
                continue;
            }
            // 获取被Autowired标记的成员变量
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String autoWiredValue = autowired.value();
                    // 获取该成员变量的类型
                    Class<?> fieldClass = field.getType();
                    // 获取成员变量在容器中对应的实例
                    Object fieldInstance = getFieldInstance(fieldClass,autoWiredValue);
                    if (fieldInstance == null) {
                        throw new RuntimeException("unable to inject relevant type, " +
                                "target fieldClass is " + fieldClass.getClass());
                    } else {
                        // 通过反射将对应的成员变量实例注入到成员变量所在的实例中
                        Object target = beanContainer.getBean(clazz);
                        ClassUtils.setField(target, field, fieldInstance, true);
                    }
                }
            }
        }
    }

    /**
     * 基于Field的类型，从Bean容器中获取实例
     * Field的类型可能是一个接口，而实际要获取的是其子类或则其实现
     *
     * @param clazz
     * @return
     */
    private Object getFieldInstance(Class<?> clazz, String autoWiredValue) {
        Object fieldInstance = beanContainer.getBean(clazz);
        // 说明该字段不是接口，有直接实例，返回即可
        if(fieldInstance != null){
            return fieldInstance;
        }
        // 可能类型是接口或者父类，需要获取其实现类
        Class<?> implClass = getImplClass(clazz,autoWiredValue);
        if(implClass != null){
            return beanContainer.getBean(implClass);
        }
        return null;
    }

    /**
     * 获取接口的实现类，返回一个
     * @param clazz
     * @return
     */
    private Class<?> getImplClass(Class<?> clazz,String autoWiredValue){
        Set<Class<?>> classSet = beanContainer.getClassBySuper(clazz);
        if(ValidationUtil.isEmpty(classSet)){
            return null;
        }
        // 解决对同一个接口有多个实现类的问题
        // Qualifier注解，进一步指定注入的实现类
        if(ValidationUtil.isEmpty(autoWiredValue)){
            // 没有指定
            if(classSet.size() == 1){
                return classSet.iterator().next();
            }else{
                throw new RuntimeException("not known which to DI");
            }
        }else{
            // 通过AutoWired注解的value指定了实现类
            for(Class<?> implClazz : classSet){
                if(implClazz.getSimpleName().equals(autoWiredValue)){
                    return implClazz;
                }
            }
            throw new RuntimeException("not known which to DI");
        }

    }

}
