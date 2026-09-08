package org.example.com.userservice.controller;

import org.example.com.userservice.common.Result;
import org.example.com.userservice.dto.LoginRequest;
import org.example.com.userservice.entity.User;
import org.example.com.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Result<Void> register(@RequestBody User user) {

        boolean success = userService.register(user);

        if (!success) {
            return Result.error(40001, "用户名已存在");
        }

        return Result.success();
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody LoginRequest request) {

        User user = userService.login(
                request.getUsername(),
                request.getPassword()
        );

        if (user == null) {
            return Result.error(40002, "用户名或密码错误");
        }

        user.setPassword(null);

        return Result.success(user);
    }
}