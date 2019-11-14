package com.example.demo.services.impl;

import com.example.demo.data.services.IUserSvc;
import com.example.demo.data.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class LoginService implements UserDetailsService, UserDetailsPasswordService {

    @Inject
    IUserSvc userSvc;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        org.springframework.security.core.userdetails.User userdetails = null;
        User user = userSvc.findByUserName(username);

        if( user != null  ){
            userdetails =new org.springframework.security.core.userdetails.User( user.getUserName(), user.getPassword(), new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("User not found:  " + username);
        }

        return userdetails;
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }


    public boolean checkPassword( String login, String password ){

        boolean result = false;

        List<User> users = userSvc.findByEmail(login);

        if( users != null &&
                users.size()> 0 &&
                users.get(0).getPassword().equals(password) ) {
            result = true;
        }

        return result;
    }

    public User registerUser( User user ){

        Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
        user.setCreatedTime( currentTime );
        user.setCreatedBy("web-registration");
        user.setUpdatedTime(currentTime);

        User user1 = userSvc.saveAndFlush(user);
        return user1;
    }
}
