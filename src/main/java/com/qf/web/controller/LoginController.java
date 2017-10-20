package com.qf.web.controller;

import com.qf.domain.User;
import com.qf.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by ios on 17/10/17.
 */
@Controller
@RequestMapping("/")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, boolean remember, HttpSession session,
                        HttpServletResponse response ,RedirectAttributes redirectAttributes) {
        String state = userService.login(user);
        switch (state) {
            case UserService.INVALID_USER:
                redirectAttributes.addFlashAttribute("errorMessage", "账号不存在");
                return "redirect:/login";
            case UserService.ACCESS:
                session.setAttribute("user", user);
                Cookie cookie = userService.rememberMe(remember, user);
                if (cookie != null) {
                    cookie.setMaxAge(24 * 3600 * 30);
                    cookie.setPath("/**");
                    response.addCookie(cookie);
                }
                return "redirect:/home";
            case UserService.WRONG_PASSWORD:
                redirectAttributes.addFlashAttribute("errorMessage", "密码错误");
                return "redirect:/login";
        }
        return "/home";
    }



    @RequestMapping("/signOut")
    public String signOut(HttpSession session, HttpServletResponse response) {
        User loginUser = (User) session.getAttribute("user");
        Cookie cookie = new Cookie("autoLogin", loginUser.getName() + "|" + loginUser.getPassword());
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:login";
    }
}
