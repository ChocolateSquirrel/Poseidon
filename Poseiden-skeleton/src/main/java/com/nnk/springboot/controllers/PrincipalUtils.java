package com.nnk.springboot.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.security.Principal;

public class PrincipalUtils {

    public static String getUserName(Principal user){
        if (user instanceof UsernamePasswordAuthenticationToken){
            return user.getName();
        }
        return ((OAuth2AuthenticationToken) user).getPrincipal().getAttributes().get("login").toString();
    }
}
