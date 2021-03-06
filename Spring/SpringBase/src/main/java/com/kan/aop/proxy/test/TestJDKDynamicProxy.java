package com.kan.aop.proxy.test;

import com.kan.aop.proxy.dynamicproxy.jdk.interceptor.ObjectInterceptor;
import com.kan.aop.proxy.business.interfaces.UserService;
import com.kan.aop.proxy.business.interfaces.impl.UserServiceImpl;
import com.kan.aop.proxy.business.transactions.MyTransaction;

import java.lang.reflect.Proxy;

public class TestJDKDynamicProxy {

    public static void main(String[] args) {
        // 目标类：需要被代理的类
        Object target = new UserServiceImpl();
        // 事务类
        MyTransaction transaction = new MyTransaction();
        // 拦截器
        ObjectInterceptor proxyObject = new ObjectInterceptor(target, transaction);
        // 动态代理对象
        UserService userService = (UserService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                proxyObject);
        // JointPoint：连接点，可能被拦截到的方法(如所有方法)
        // PointCut：切入点，已经被增强的方法
        userService.addUser(null);
    }
}