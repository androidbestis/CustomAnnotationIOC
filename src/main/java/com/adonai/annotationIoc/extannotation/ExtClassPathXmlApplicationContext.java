
package com.adonai.annotationIoc.extannotation;

import com.adonai.annotationIoc.utils.ClassUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 手写SpringIoc注解版本
 */
public class ExtClassPathXmlApplicationContext {

    //扫包的范围
    private String packageName = null;
    private static ConcurrentHashMap<String,Class<?>> beans = null;

    /**
     * @param packageName  需要扫包的包名
     */
    public ExtClassPathXmlApplicationContext(String packageName) throws Exception {
        if(StringUtils.isNotEmpty(packageName)){
            this.packageName = packageName;
            beans = new ConcurrentHashMap<String, Class<?>>();
            initBeans();
        }
    }

    //初始化对象
    public void initBeans() throws Exception {
     //1.使用Java的反射机制扫包,获取当前包下所有的类
        List<Class<?>> classes = ClassUtil.getClasses(packageName);
     //2.判断类上是否存在注入Bean的注解
        ConcurrentHashMap<String, Class<?>> classExitsAnnotation = findClassExitsAnnotation(classes);
        if(classExitsAnnotation == null || classExitsAnnotation.isEmpty()){
          throw new Exception("该包下面没有加注解的类");
        }
    }

    //3.使用Java的反射机制进行初始化
    public Object getBean(String beanName) throws Exception {
        if(StringUtils.isEmpty(beanName)){
            throw new Exception("beanName不能为空");
        }
        //从beans容器中获取该Bean
        Class<?> classInfo = beans.get(beanName);
        if(classInfo == null){
            throw new Exception("Class Not Found");
        }
        return newInstance(classInfo);
    }

    //初始化对象
    public Object newInstance(Class<?> classInfo) throws Exception {
        return classInfo.newInstance();
    }

    //循环遍历包下加注解类,添加到容器中
    public ConcurrentHashMap<String,Class<?>> findClassExitsAnnotation(List<Class<?>> classe){
        //循环遍历所有类
        for (Class<?> classInfo: classe) {
            //判断类上面是否有ExtService注解
            ExtService serviceAnnotation = classInfo.getAnnotation(ExtService.class);
            if(serviceAnnotation != null){
                String simpleName = classInfo.getSimpleName();
                String newClassName = toLowerCaseFirstOne(simpleName);
                //类上有注解,则加到容器里面
                beans.put(newClassName,classInfo);
            }
        }
        return beans;
    }


    // 首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

}
