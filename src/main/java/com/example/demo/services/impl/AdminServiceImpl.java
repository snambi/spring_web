package com.example.demo.services.impl;

import com.example.demo.data.models.Role;
import com.example.demo.data.models.User;
import com.example.demo.data.services.IRoleSvc;
import com.example.demo.data.services.IUserSvc;
import com.example.demo.security.RoleEnum;
import com.example.demo.services.IAdminService;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Calendar;

public class AdminServiceImpl implements IAdminService {

    @Inject
    private IUserSvc userSvc;

    @Inject
    private IRoleSvc roleSvc;

    @Override
    public void upgradeToModerator(User user) {

        // get the user
        User u = userSvc.findByUserName(user.getUserName());

        // update the role
        Role role = new Role();

        role.setUserName(u.getUserName());
        role.setRole(RoleEnum.ROLE_MODERATOR.name());
        role.setCreatedBy("Web");

        Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
        role.setUpdatedTime( currentTime);

        // update the Role
        roleSvc.saveAndFlush(role);
    }
}
