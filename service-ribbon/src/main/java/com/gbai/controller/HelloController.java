package com.gbai.controller;

import com.gbai.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: service-ribbon
 * @description:
 * @author: gbai
 * @create: 2018-06-06 14:52
 **/
@RestController
public class HelloController {

    @Autowired
    HelloService helloService;
    @RequestMapping("/hello")
    public String hello(@RequestParam String name){

        return helloService.hiService(name);
    }
}
