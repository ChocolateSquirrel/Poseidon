package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("app")
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        log.info("Request GET : /app/login");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        log.info("Request : OK");
        return mav;
    }

    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    @GetMapping("/error")
    public ModelAndView error(Principal user) {
        log.info("Request GET : /app/error");
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.addObject("connectedUser", PrincipalUtils.getUserName(user));
        mav.setViewName("403");
        log.info("Request : OK");
        return mav;
    }

    @RequestMapping("logout")
    public ModelAndView logout() {
        log.info("Request POST : /app/logout");
        ModelAndView mav = new ModelAndView();
        SecurityContextHolder.clearContext();
        mav.setViewName("home");
        log.info("Request : OK");
        return mav;
    }
}
