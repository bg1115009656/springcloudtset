package com.gbai.feignInter;

import com.gbai.hystrixHandler.FallBackHandler;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: feign-demo
 * @description:
 * @author: gbai
 * @create: 2018-06-06 15:20
 **/
@FeignClient(value = "service-hi",fallback = FallBackHandler.class)
public interface FeignService {
    @GetMapping(value = "/hello")
    String sayHiFromClientOne(@RequestParam("name") String name);
}
