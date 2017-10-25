package com.qf.service;

import com.qf.domain.User;
import com.qf.mapper.UserMapper;
import com.qf.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

/**
 * Created by ios on 17/10/17.
 */
@Service
public class UserService {
    public static final String INVALID_USER = "noUser";
    public static final String ACCESS = "access";
    public static final String WRONG_PASSWORD = "wrongPwd";

    @Autowired
    private UserMapper userMapper;

    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    public String login(User user) {
        User myUser = userMapper.findByName(user.getName());
        if(myUser == null) {
            return INVALID_USER;
        } else {
            if (MD5Utils.md5(myUser.getPassword()).equals(user.getPassword())) {
                return ACCESS;
            } else {
                return WRONG_PASSWORD;
            }
        }
    }

    public Cookie rememberMe(Boolean remember, User user) {
        if (remember) {
            return new Cookie("autoLogin", user.getName() + "|" + user.getPassword());
        }
        return null;
    }
}
