package org.example.com.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.com.userservice.entity.User;
import org.example.com.userservice.mapper.UserMapper;
import org.example.com.userservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public boolean register(User user) {
        //查询条件构造器
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());

        User exist = getOne(wrapper);

        if (exist != null) {
            return false;
        }

        return save(user);
    }

    @Override
    public User login(String username, String password) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(User::getUsername, username)
                .eq(User::getPassword, password);

        return getOne(wrapper);
    }
}