package com.tobilko.spring.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;


/**
 *
 * Created by Andrew Tobilko on 23/09/16.
 *
 */
@SpringBootApplication
public class Starter {

    public static void main(String[] args) { run(Starter.class, args); }

    private @Autowired TestBeanA a;
    private @Autowired TestBeanB b;

}
