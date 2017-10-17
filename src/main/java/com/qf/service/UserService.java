package com.qf.service;

import com.qf.domain.User;
import com.qf.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ios on 17/10/17.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findByName(String name) {
        return userMapper.findByName(name);
    }
}
