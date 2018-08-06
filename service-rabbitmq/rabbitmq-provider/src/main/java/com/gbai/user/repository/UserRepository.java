package com.gbai.user.repository;

import com.gbai.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: service-rabbitmq
 * @description:
 * @author: gbai
 * @create: 2018-08-03 14:19
 **/
public interface UserRepository extends JpaRepository<UserEntity,Long>
{

}
