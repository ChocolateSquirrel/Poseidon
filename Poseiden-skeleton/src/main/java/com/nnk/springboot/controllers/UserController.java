package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/list")
    public String home(Model model) {
        log.info("Request : /user/list");
        model.addAttribute("users", userRepository.findAll());
        log.info("Response for /user/list : there are " + userRepository.findAll().size() + " users");
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user, Model model) {
        log.info("Request GET : /user/add");
        model.addAttribute("user", user);
        log.info("Response : OK");
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        log.info("Request POST : /user/validate");
        if (!result.hasErrors()) {
            if (!UserServiceImpl.isValid(user.getPassword())){
                log.error("Response : password is not correct, must have 8 characters...");
                return "user/add";
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("users", userRepository.findAll());
            log.info("Response : user added " + user.getId()
                    + " (fullname:" + user.getFullname()
                    + ", username:" + user.getUsername()
                    + ", role:" + user.getRole() + ")");
            return "redirect:/user/list";
        }
        log.error("Response : " + result.getErrorCount() + "errors");
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /user/update/{id}");
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        log.info("Response : user to update : " + user.getId());
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        log.info("Request POST : /user/update/{id}");
        if (result.hasErrors()) {
            log.error("Response : " + result.getErrorCount() + "errors");
            return "user/update";
        }

        if (!UserServiceImpl.isValid(user.getPassword())){
            log.error("Response : password is not correct, must have 8 characters...");
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        log.info("Response : user updated " + user.getId()
                + " (fullname:" + user.getFullname()
                + ", username:" + user.getUsername()
                + ", role:" + user.getRole() + ")");
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /user/delete/{id}");
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        log.info("Response : user to delete : " + user.getId());
        return "redirect:/user/list";
    }
}
