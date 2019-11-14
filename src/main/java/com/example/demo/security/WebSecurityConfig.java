package com.example.demo.security;

import com.example.demo.services.impl.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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

        // enable REST Apis with different level of authentication
        http
            .httpBasic().disable()
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/news/*").anonymous()
                .antMatchers(HttpMethod.POST, "/api/v1/news").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/news/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/news/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/news/*").permitAll()
                .and()


            // enable REST Apis with different level of authentication

                //"**/swagger-resources/**", "/v2/api-docs", "/webjars/**",
            .authorizeRequests()
                .antMatchers( "/", "/home", "/login", "/test", "/news", "/signup",
                        "/css/*", "/js/*",
                        "/swagger-ui.html*", "/webjars/**", "/swagger-resources/**", "/v2/api-docs",
                        "/bootstrap-4.3.1/css/*",  "/bootstrap-4.3.1/js/*" )
                .permitAll()
                .anyRequest().authenticated()
                .and()


            // Login Configuration
            .formLogin()
                .loginPage("/login")
                .usernameParameter("userName")
                .successHandler( myAuthenticationSuccessHandler() )
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
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .deleteCookies("JESSIONID")
                .permitAll();

    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new AuthenticationSuccessHandlerImpl();
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

    @Bean("sample_text")
    public String doSomething() {
        return "hello world";
    }
}
