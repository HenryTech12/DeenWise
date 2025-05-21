package com.deenwise.demo.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomSuccessAuthenticationHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object roles[] =  authentication.getAuthorities().toArray();
        for(Object role: roles) {
            System.out.println("ROLE: "+role);
        }
        if(roles[0].toString().equals("ROLE_TEACHER"))
            response.sendRedirect("/teacher/dashboard");
        else
            response.sendRedirect("/student/dashboard");
    }
}
