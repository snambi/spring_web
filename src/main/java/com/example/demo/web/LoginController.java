package com.example.demo.web;

import com.example.demo.data.models.User;
import com.example.demo.services.impl.LoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response){
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//
//        // delete all the cookies
//        Cookie[] cookies = request.getCookies();
//        for( Cookie cookie : cookies ){
//            cookie.setMaxAge(0);
//            response.addCookie(cookie);
//        }
//
//        return "redirect:/";
//    }

    @GetMapping("/signup")
    public String signup( Model model ){
        model.addAttribute("signup", new Signup());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@Valid Signup signup, BindingResult bindingResult){

        // do the form validations
        if( signup.getEmailId().isEmpty()){
            bindingResult.addError(new FieldError("signup", "emailId","Email cannot be empty"));
        }else{
            if( !signup.getEmailId().contains("@") &&
                    !signup.getEmailId().contains(".")){
                bindingResult.addError(new FieldError("signup", "emailId","Email should be valid"));
            }
        }

        if( signup.getPassword().isEmpty() ){
            bindingResult.addError( new FieldError("signup", "password","Password cannot be empty"));
        }else{
            if( signup.getPassword().length() < 8 ){
                bindingResult.addError(new FieldError("signup", "password","Password must be atleast 8 characters"));
            }
        }

        // check whether the  userid is already in use?
        if( loginService.isUserIdInUse(signup.getUserName())){
            bindingResult.addError(new FieldError("signup", "userName", signup.getUserName() + " is already in use"));
        }


        if( bindingResult.hasErrors() ){
            return "signup";
        }

        User user = new User();

        user.setEmail( signup.getEmailId() );
        user.setPassword(  passwordEncoder.encode( signup.getPassword() ) );
        user.setUserName( signup.getUserName() );

        loginService.registerUser(user);

        return "redirect:/mydata";
    }

    public static class Signup{

        @NotNull
        private String emailId;

        @NotNull
        @Size( min = 6, max = 128)
        private String password;

        @NotNull
        @Size( min = 6, max = 128)
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
