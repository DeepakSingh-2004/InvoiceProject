package com.springboottest.agency.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboottest.agency.Entity.UserEntity;
import com.springboottest.agency.Service.UserService;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService userService ;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserEntity userEntity) {
    return userService.addUser(userEntity);
 }


}
