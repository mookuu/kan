package org.moku.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName ClientController
 * @Description 从EurekaServer中获取服务提供方的服务地址信息
 * @Author moku
 * @Date 2023/3/1 1:13
 * @Version 1.0
 */
@Controller
public class ClientController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/queryService")
    @ResponseBody
    public String query() {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-provider1");
        StringBuilder urls = new StringBuilder();
        for (ServiceInstance instance : instances) {
            urls.append(instance.getHost() + ":" + instance.getPort()).append(",");
        }
        return urls.toString();
    }
}
