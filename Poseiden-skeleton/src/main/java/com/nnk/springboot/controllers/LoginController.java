package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("app")
public class LoginController {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UserRepository userRepository;

    public LoginController(OAuth2AuthorizedClientService authorizedClientService, UserRepository userRepository) {
        this.authorizedClientService = authorizedClientService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
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
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

    @PostMapping("logout")
    public ModelAndView logout() {
        ModelAndView mav = new ModelAndView();
        SecurityContextHolder.clearContext();
        mav.setViewName("home");
        return mav;
    }

    // Ajouté pour OAuth2 mais ça ne fonctionne pas... problème d'url ?

    /**
     * Access to protected info of user in access token
     * @param user
     * @return
     */
    @GetMapping("/*")
    public String getUserInfo(Principal user) {
        StringBuffer userInfo = new StringBuffer();
        if (user instanceof UsernamePasswordAuthenticationToken){
            userInfo.append(getUsernamePasswordLoginInfo(user));
        }
        else if (user instanceof OAuth2AuthenticationToken){
            userInfo.append(getOAuth2LoginInfo(user));
        }
        return userInfo.toString();
    }

    /**
     * Fetch username and authenticate token
     * @param user
     * @return
     */
    private StringBuffer getUsernamePasswordLoginInfo(Principal user){
        StringBuffer usernameInfo = new StringBuffer();
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;
        if (token.isAuthenticated()){
            User u = (User) token.getPrincipal();
            usernameInfo.append("Welcome, " + u.getUsername());
        }
        else {
            usernameInfo.append("NA");
        }
        return usernameInfo;
    }

    /**
     * Fetch access token
     * @param user
     * @return
     */
    private StringBuffer getOAuth2LoginInfo(Principal user){
        StringBuffer protectedInfo = new StringBuffer();
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) user;
        OAuth2AuthorizedClient authClient = authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(),
                authToken.getName());
        if (authToken.isAuthenticated()){
            Map<String, Object> userAttributes = authToken.getPrincipal().getAttributes();
            String userToken = authClient.getAccessToken().getTokenValue();
            protectedInfo.append("Welcome, " + userAttributes.get("name")+"<br><br>");
            //protectedInfo.append("e-mail: " + userAttributes.get("email")+"<br><br>");
            protectedInfo.append("Access Token: " + userToken+"<br><br>");
        }
        else{
            protectedInfo.append("NA");
        }
        return protectedInfo;
    }
}
