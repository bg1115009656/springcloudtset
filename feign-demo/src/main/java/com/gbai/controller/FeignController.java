package com.gbai.controller;

import com.gbai.feignInter.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: feign-demo
 * @description:
 * @author: gbai
 * @create: 2018-06-06 15:28
 **/
@RestController
public class FeignController {
    @Autowired
    FeignService feignService;
    @RequestMapping(value = "/hello")
    public String sayHello(@RequestParam String name){
        return feignService.sayHiFromClientOne(name);
    }
}
