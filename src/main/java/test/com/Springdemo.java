package com;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan()
public class Springdemo {

    public static void main(String[] args) {
        ApplicationContext  context =
                new AnnotationConfigApplicationContext(Springdemo.class);
        SpirngBean bean = context.getBean(SpirngBean.class);
        bean.print();

    }
}
