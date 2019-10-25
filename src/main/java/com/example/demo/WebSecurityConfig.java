package com.example.demo;

import com.example.demo.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
//        auth.inMemoryAuthentication()
//            .withUser("user").password("password").roles("USER")
//            .and()
//            .withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/home", "/login", "/signup", "/css/*", "/js/*", "/bootstrap-4.3.1/css/*",  "/bootstrap-4.3.1/js/*" ).permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("emailId")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Bean
    public UserDetailsService userDetailsService() {

//        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(userBuilder.username("user").password( "password").roles("USER").build());
//        manager.createUser(userBuilder.username("ss@gg").password( "test" ).roles("USER").build());
//        manager.createUser(userBuilder.username("admin").password( "password" ).roles("USER", "ADMIN").build());
//
//        return manager;

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
