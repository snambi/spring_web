package com.example.demo.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.WebAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Configuration
public class Utils {

    @Bean("ThymeLeafUtils")
    public ThymeLeafUtils getUserLoggedin(){
        return new ThymeLeafUtils();
    }

    public static class ThymeLeafUtils {

        public boolean isLoggedin( HttpServletRequest request ){

            boolean isLoggedin = false;
            HttpSession session = request.getSession(false);

            if (session != null) {

                SecurityContext securityContext = (SecurityContext)  session.getAttribute("SPRING_SECURITY_CONTEXT");
                if( securityContext != null ){
                    if( securityContext.getAuthentication().isAuthenticated() ){
                        isLoggedin = true;
                    }
                }

                Enumeration attrs = session.getAttributeNames();
                while( attrs.hasMoreElements() ){
                    String attr = (String) attrs.nextElement();
                    System.out.println("Session Attribute : "+ attr);
                }

                for(Cookie cookie : request.getCookies() ){
                    System.out.println( "Cookie : " + cookie.getName() +" = " + cookie.getValue());
                }
            }

            return isLoggedin;
        }


        public boolean isLandingPageAndLoggedin(  HttpServletRequest request ){

            boolean result = false;

            if( isLoggedin( request) &&
                isHomePage(request )){
                result = true;
            }

            return result;
        }


        public boolean isLandingPageAndNotLoggedIn( HttpServletRequest request  ){

            boolean result = false;

            if( isHomePage(request) &&
                    !isLoggedin(request)){
                result = true;
            }

            return result;
        }


        public boolean hasLoginFailed(  HttpServletRequest request ){

            boolean result = false;

            if( getLoginError(request) != null ){
                result = true;
            }

            return result;
        }


        public String getLoginError( HttpServletRequest request ){

            String error = null;

            if( request.getSession() != null &&
                    request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null ){

                Object err = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

                if( err instanceof Throwable ){
                    error = ((Throwable) err).getMessage();
                }
            }

            return error;
        }

        public boolean isHomePage(  HttpServletRequest request ){

            boolean result = false;
            if( request != null ){

                if( request.getRequestURI().equalsIgnoreCase("/") ||
                        request.getRequestURI().equals("")){

                    result = true;
                }
            }

            return result;
        }


        public boolean hasLoggedInBefore( HttpServletRequest request ){

            boolean result = isLoggedin(request);

            // check whether the usernme cookie is present
            if( result == false ){

                if( request.getCookies() != null ){
                    for( Cookie cookie : request.getCookies() ){
                        if( cookie.getName().equals("username")){
                            result = true;
                        }
                    }
                }
           }

            return result;
        }
    }
}
