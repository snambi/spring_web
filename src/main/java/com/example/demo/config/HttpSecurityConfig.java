package com.example.demo.config;

import com.example.demo.security.AuthenticationSuccessHandlerImpl;
import com.example.demo.security.LogoutSuccessHandlerImpl;
import com.example.demo.services.impl.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

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
                .antMatchers(HttpMethod.POST, "/api/v1/news").hasRole("USER")
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
                .antMatchers("/vote").hasRole("MODERATOR")
                .antMatchers("/block").hasRole("STAFF")
                .antMatchers("/admin").hasRole("ADMIN")
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
                .addLogoutHandler( myLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JESSIONID")
                .permitAll();

    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl r = new RoleHierarchyImpl();
        r.setHierarchy("ROLE_ADMIN > ROLE_STAFF > ROLE_MODERATOR > ROLE_USER");
        return r;
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new AuthenticationSuccessHandlerImpl();
    }

    @Bean
    public LogoutHandler myLogoutSuccessHandler(){
        return new LogoutSuccessHandlerImpl();
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
