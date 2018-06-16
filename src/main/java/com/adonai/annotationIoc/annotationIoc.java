package com.adonai.annotationIoc;

import com.adonai.annotationIoc.extannotation.ExtClassPathXmlApplicationContext;
import com.adonai.annotationIoc.service.UserService;

/***
 * 注解方式SpringIOC
 */
public class annotationIoc {

     public static void main(String[] args) throws Exception {
          ExtClassPathXmlApplicationContext classPath = new ExtClassPathXmlApplicationContext("com.adonai.annotationIoc.service");
          UserService userService = (UserService)classPath.getBean("userService");
          userService.add();
     }
}
