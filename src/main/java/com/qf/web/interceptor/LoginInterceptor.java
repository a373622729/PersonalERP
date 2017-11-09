package com.qf.web.interceptor;

import com.qf.domain.User;
import com.qf.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by ios on 17/10/17.
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String      url     = request.getRequestURI();

        HttpSession session = request.getSession();
        User        loginUser    = (User) session.getAttribute("user");
        if (loginUser != null) {
            return true;
        } else {
            Cookie[] cookies = request.getCookies();
            String username = "";
            String password = "";
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("autoLogin")) {
                        String autoLogin = cookie.getValue();
                        String ss[] = autoLogin.split("\\|");
                        username = ss[0];
                        password = ss[1];
                        break;
                    }
                }

            }
            if (!"".equals(username) && !"".equals(password)) {
                User user  = new User();
                user.setName(username);
                user.setPassword(password);
                if (userService.login(user).equals(UserService.ACCESS)) {
                    request.getSession().setAttribute("user", userService.getLoginUser(user));
                    return true;
                }
            } else {
                response.sendRedirect("/login");
                return false;
            }
        }
        return false;
    }

}
