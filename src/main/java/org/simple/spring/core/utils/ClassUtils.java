package org.simple.spring.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: LiJiaChang
 * @Date: 2020/7/31 23:12
 */
@Slf4j
public class ClassUtils {

    public static final String FILE_STRING = "file";

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
            System.out.println("无法获取到该package" + packageName);
            return null;
        }
        // 基于不同的资源类型，采用不同的方式获取资源的集合
        Set<Class<?>> result = null;
        if(FILE_STRING.equalsIgnoreCase(url.getProtocol())){
            result = new HashSet<>();
            // 获取文件目录
            File packageDirectory = new File(url.getPath());
            extraClassFile(result,packageDirectory,packageName);
            return result;
        }
        return null;
    }

    private static void extraClassFile(Set<Class<?>> set, File directory, String packageName){

    }



    /**
     * 获取当前项目的类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static void main(String[] args){
        extraPackageClass("org.simple.spring");
    }

}
