package com.srini;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@RibbonClient(value = "user", configuration = UserClientConfiguration.class)
public class UserClientConfiguration {
    @Bean
    public ILoadBalancer ribbonLoadBalancer() {
        BaseLoadBalancer balancer = new BaseLoadBalancer();
        balancer.setServersList(Collections.singletonList(new Server("localhost", 9999)));
        return balancer;
    }
}
