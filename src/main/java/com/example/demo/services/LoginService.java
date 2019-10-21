package com.example.demo.services;

import com.example.demo.db.services.IUserSvc;
import com.example.demo.entities.User;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
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
}
