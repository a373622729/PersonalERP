package com.qf.web.controller;

import com.qf.domain.User;
import com.qf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by ios on 17/10/17.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        System.out.println("login---GET");
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "name", required = true) String name,
                        @RequestParam(value = "password", required = true) String pwd,
                        @RequestParam(value = "remember", required = false) boolean remember,
                        Model model, HttpServletRequest request) {
        System.out.println("login---POST");
        User user = userService.findByName(name);
        if(user == null) {
            model.addAttribute("errorMessage", "账户不存在");
            return "login";
        } else {
            if (pwd.equals(user.getPassword())) {
                if (remember) {
                    request.getSession().setAttribute("user", user);
                }
                return "home";
            } else {
                model.addAttribute("errorMessage", "密码错误");
                return "login";
            }
        }
    }

    @RequestMapping("/signOut")
    public String signOut(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        model.addAttribute("errorMessage", "用户已经注销");
        return "login";
    }
}
