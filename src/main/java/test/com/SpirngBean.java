package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SpirngBean {

    public void print(){
        System.out.println("这是一个bean实例");
    }
}
