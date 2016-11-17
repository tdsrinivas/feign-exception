package com.srini.controller;

import com.srini.model.User;
import com.srini.remote.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserClient userClient;

    @RequestMapping(value = "/users/{guid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody User getUser(@PathVariable String guid) {
        User user = userClient.getUser(guid);
        return user;
    }
}
