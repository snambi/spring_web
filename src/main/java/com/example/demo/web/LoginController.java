package com.example.demo.web;

import com.example.demo.entities.User;
import com.example.demo.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.inject.Inject;

@Controller
public class LoginController {

    @Inject
    LoginService loginService;

    @GetMapping("/login")
    public String  login( Model model ){
        model.addAttribute("login", new Login());
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit( Login login, Model model ){

        System.out.println( "email: "+ login.getEmailId() + ", password: "+ login.getPassword());

        boolean result = loginService.checkPassword(login.getEmailId(), login.getPassword());

        return "redirect:/home";
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
        user.setPassword( signup.getPassword());
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

        private String emailId;
        private String password;

        public Login() {
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
    }
}
