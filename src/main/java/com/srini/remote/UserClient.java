package com.srini.remote;

import com.srini.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name = "user")
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{guid}")
    User getUser(@PathVariable("guid") String guid);
}
