package org.example.com.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.com.securityplatform.jwt.JwtTokenProvider;
import org.example.com.userservice.entity.User;
import org.example.com.userservice.mapper.UserMapper;
import org.example.com.userservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    public UserServiceImpl(PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider){
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public boolean register(User user) {
        //查询条件构造器
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());

        User exist = getOne(wrapper);

        if (exist != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    @Override
    public String login(String username, String password) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(User::getUsername, username);
        User user = getOne(wrapper);
        if(user == null){
            return null;
        }
        boolean mathches = passwordEncoder.matches(
                password,
                user.getPassword()
        );
        if(!mathches){
            return null;
        }

        return jwtTokenProvider.generateToken(
                user.getId(),
                user.getUsername()
        );
    }
}