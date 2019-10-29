package com.example.demo.web;

import com.example.demo.data.models.User;
import com.example.demo.services.LoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Inject
    LoginService loginService;

    @Inject
    PasswordEncoder passwordEncoder;

    /**
     * login page is created by the following method
     *
     * post login processing is handled by <code>AuthenticationSuccessHandlerImpl</code>
     *
     */
    @GetMapping("/login")
    public String  login( Model model ){
        model.addAttribute("login", new Login());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signup( Model model ){
        model.addAttribute("signup", new Signup());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit( Signup signup, Model model ){

        User user = new User();

        user.setEmail( signup.getEmailId() );
        user.setPassword(  passwordEncoder.encode( signup.getPassword() ) );
        user.setUserName( signup.getUserName() );

        loginService.registerUser(user);

        return "redirect:/mydata";
    }

    public static class Signup{
        private String emailId;
        private String password;
        private String userName;

        public Signup() {
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class Login{

        private String userName;
        private String password;

        public Login() {
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
