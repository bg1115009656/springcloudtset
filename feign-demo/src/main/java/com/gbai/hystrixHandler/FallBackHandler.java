package com.gbai.hystrixHandler;

import com.gbai.feignInter.FeignService;
import org.springframework.stereotype.Component;

/**
 * @program: feign-demo
 * @description:
 * @author: gbai
 * @create: 2018-06-06 16:15
 **/
@Component
public class FallBackHandler implements FeignService {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry:"+name;
    }
}
