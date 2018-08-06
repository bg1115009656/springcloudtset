package com.gbai.user.controller;

import com.gbai.user.entity.UserEntity;
import com.gbai.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: service-rabbitmq
 * @description:
 * @author: gbai
 * @create: 2018-08-03 15:03
 **/
@RestController
@RequestMapping(value = "/user")
public class UserController
{
    /**
     * 用户业务逻辑
     */
    @Autowired
    private UserService userService;

    /**
     * 保存用户基本信息
     * @param
     * @return
     */
    @RequestMapping(value = "/save")
    public UserEntity save(String userName) throws Exception
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userService.save(userEntity);
        return userEntity;
    }
}
