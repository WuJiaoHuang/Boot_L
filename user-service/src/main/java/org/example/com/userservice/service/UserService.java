package org.example.com.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.com.userservice.entity.User;

public interface UserService extends IService<User> {

    boolean register(User user);

    User login(String username, String password);
}