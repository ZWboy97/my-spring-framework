# MySimpleSpring

## 基础
### 1. IOC 创建Bean的过程
1. 查Bean的config, 看依赖哪些其他类
2. 递归的查看各个依赖的config, 直到最底层的类
3. 然后回溯过程中, 从下向上new 实例, 并注入到上层的实例中
4. 最终实现Bean实例的创建

### 2. IOC容器实现思路
1. 创建注解
2. 提取标记对象
3. 实现IOC容器
4. 实现依赖注入DI

## IOC实现
### 1. 定义注解
- 基本与Spring原框架保持一直，最后悄悄能不能直接换用自己的框架
```java
@Controller
@Service
@Repository
@Component
```

### 2. 提取标记对象
- 明确实现内容：提取有特定标记的对象，并将它们注册到容器中
#### 实现思路
1. 读取配置中的扫描范围，范围由框架使用者提供


1. 通过遍历，获取范围中的所有类 
```java
//基于package，获取该package下所有的类
public static Set<Class<?>> extraPackageClass(String packageName){
}
```
     

1. 遍历所有类，获取将注解标记的类，并加载注册到容器中




### 3.实现容器
- 默认将类名作为key，存储到容器中

- 容器的组成部分
    - 保存class对象及其实例的载体
    - 容器的加载
    - 容器的操作方式
    
- 容器的加载
    - 配置的管理与获取
    - 获取指定范围内的class对象
    - 依据配置获取class对象，并存入容器载体中
    

### 实现依赖注入DI