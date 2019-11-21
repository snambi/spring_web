package com.example.demo.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;

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
