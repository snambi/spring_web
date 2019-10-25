package com.example.demo;

import com.example.demo.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    LoginService loginService;

    @Inject
    private DataSource dataSource;

    @Override
    protected void configure( AuthenticationManagerBuilder auth)
            throws Exception {

        auth.userDetailsService( userDetailsService())
        .passwordEncoder(encoder())
        .and()
        .authenticationProvider( authenticationProvider())
        .jdbcAuthentication()
        .dataSource( dataSource )
        .passwordEncoder( encoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/home", "/login", "/signup", "/css/*", "/js/*", "/bootstrap-4.3.1/css/*",  "/bootstrap-4.3.1/js/*" ).permitAll()
                .anyRequest().authenticated()
                .and()

            // Login Configuration
            .formLogin()
                .loginPage("/login")
                .usernameParameter("userName")
                .permitAll()
                .and()

            //remember me configuration
            .rememberMe()
                .key("login-key")
                .rememberMeParameter("login-value")
                .rememberMeCookieName("mydata-login")
                .tokenValiditySeconds(86400)
                .and()

            .logout()
                .permitAll();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return loginService;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(loginService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

}
