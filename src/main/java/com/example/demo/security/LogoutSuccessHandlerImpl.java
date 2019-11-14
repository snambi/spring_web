package com.example.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.GregorianCalendar;

public class LogoutSuccessHandlerImpl implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // delete all the cookies
        Cookie[] cookies = request.getCookies();
        for( Cookie cookie : cookies ){
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}
