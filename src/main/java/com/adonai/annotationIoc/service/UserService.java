package com.adonai.annotationIoc.service;

import com.adonai.annotationIoc.extannotation.ExtService;

@ExtService
public class UserService {

    public void add(){
        System.out.println("添加了一个用户");
    }
}
