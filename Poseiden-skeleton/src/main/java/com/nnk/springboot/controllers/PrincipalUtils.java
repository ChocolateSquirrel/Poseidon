package com.nnk.springboot.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PrincipalUtils {

    public static String getUserName(Principal user){
        if (user instanceof UsernamePasswordAuthenticationToken){
            return user.getName();
        }
        return ((OAuth2AuthenticationToken) user).getPrincipal().getAttributes().get("login").toString();
    }

    public static boolean isAdmin(Principal user) {
        boolean isAdmin = false;
        Collection<? extends GrantedAuthority> authorities;
        if (user instanceof UsernamePasswordAuthenticationToken){
            authorities = ((UsernamePasswordAuthenticationToken) user).getAuthorities();
            List<? extends GrantedAuthority> adminAuth = authorities.stream().filter(auth -> auth.getAuthority().equals("ADMIN")).collect(Collectors.toList());
            isAdmin = adminAuth.isEmpty() ? false : true;
        }
        else {
            authorities = ((OAuth2AuthenticationToken) user).getPrincipal().getAuthorities();
            List<? extends GrantedAuthority> adminAuth = authorities.stream().filter(auth -> auth.getAuthority().equals("ROLE_ADMIN")).collect(Collectors.toList());
            isAdmin = adminAuth.isEmpty() ? false : true;
        }
        return isAdmin;
    }

}
