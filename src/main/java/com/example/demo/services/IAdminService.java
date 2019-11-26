package com.example.demo.services;

import com.example.demo.data.models.User;

public interface IAdminService {

    public void upgradeToModerator(User user);

}
