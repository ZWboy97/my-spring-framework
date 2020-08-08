package org.simple.spring.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: LiJiaChang
 * @Date: 2020/7/31 23:12
 */
@Slf4j
public class ClassUtils {

    public static final String FILE_STRING = "FILE";

    /**
     * 基于package，获取该package下所有的类
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> extraPackageClass(String packageName) {
        // 获取类加载器，目的是获取框架使用者项目发布的实际路径
        ClassLoader classLoader = getClassLoader();
        // 通过类加载器获取加载的资源信息
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            log.warn("无法获取到该package" + packageName);
            return null;
        }
        // 基于不同的资源类型，采用不同的方式获取资源的集合
        Set<Class<?>> result = null;
        if (FILE_STRING.equalsIgnoreCase(url.getProtocol())) {
            result = new HashSet<>();
            // 获取文件目录
            File packageDirectory = new File(url.getPath());
            extraClassFile(result, packageDirectory, packageName);
            return result;
        }
        return null;
    }

    /**
     * 递归获取目标package里边的所有class文件，包括子package里的class文件
     *
     * @param set         用于递归中搜集文件和目录
     * @param fileSource
     * @param packageName
     */
    private static void extraClassFile(Set<Class<?>> set, File fileSource, String packageName) {
        // 如果是文件
        if (!fileSource.isDirectory()) {
            return;
        }
        // 如果是文件夹,则调用listFiles获取文件夹下所有文件和文件夹
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    String absoluteFilePath = file.getAbsolutePath();
                    if (absoluteFilePath.endsWith(".class")) {
                        // 如果是class文件，则直接加载
                        addToClassSet(absoluteFilePath);
                    }
                }
                return false;
            }

            /**
             * 根据class文件绝对路径，获取并生成class对象，并放入classSet中
             * @param absoluteFilePath
             */
            private void addToClassSet(String absoluteFilePath) {
                // 从class绝对路径中提取出包含package的类名
                absoluteFilePath = absoluteFilePath.replace(File.separator, ".");
                // 获取类的全路径
                String className = absoluteFilePath.substring(absoluteFilePath.lastIndexOf(packageName));
                className = className.substring(0, className.lastIndexOf("."));

                // 通过反射机制，获取对应的class对象，并加入到classSet中
                Class targetClass = loadClass(className);
                set.add(targetClass);
            }
        });
        if (files != null) {
            for (File file : files) {
                extraClassFile(set, file, packageName);
            }
        }
    }

    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前项目的类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static <T> T newInstance(Class<?> clazz, boolean accessPrivate) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessPrivate);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Set<Class<?>> classSet = extraPackageClass("org.simple.spring.core");
        classSet.forEach(item -> {
            System.out.println(item);
        });
    }

}
