package com.example.demo.services;

import com.example.demo.db.services.IUserSvc;
import com.example.demo.entities.User;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Component
public class LoginService {

    @Inject
    IUserSvc userSvc;

    public boolean checkPassword( String login, String password ){

        boolean result = false;

        List<User> users = userSvc.findByEmail(login);

        if( users.get(0).getPassword().equals(password) ) {
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
