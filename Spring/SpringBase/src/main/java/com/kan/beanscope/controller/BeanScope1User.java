package com.kan.beanscope.controller;

import com.kan.beanscope.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @ClassName BeanScope1User
 * @Description
 * @Author moku
 * @Date 2022/12/10 23:23
 * @Version 1.0
 */
@Component
public class BeanScope1User {
    // 注入User类型的Bean(Bean名称：userInComponent)
    @Bean
    public User userInComponent() {
        User user = new User();
        user.setId(1);
        user.setName("Java");
        return user;
    }
}
