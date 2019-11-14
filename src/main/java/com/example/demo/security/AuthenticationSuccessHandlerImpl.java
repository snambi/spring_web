package com.example.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private static final String SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // get the username
        User user = (User) authentication.getPrincipal();

        // drop a persistent cookie after login
        Cookie cookie = new Cookie("username", user.getUsername());
        cookie.setComment("login cookie");
        cookie.setMaxAge( 24 * 60 * 60 );
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        // redirect to the appropriate page
        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);

        if (session != null) {
            SavedRequest savedRequest = (SavedRequest) session.getAttribute(SAVED_REQUEST);
            if( savedRequest != null && savedRequest.getRedirectUrl() != null ){
                uri = savedRequest.getRedirectUrl();
            }
        }

        if( uri.equals("/login")){
            uri = "/home";
        }

        redirectStrategy.sendRedirect(request, response, uri);
    }
}
