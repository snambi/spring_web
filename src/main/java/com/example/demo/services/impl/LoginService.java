package com.example.demo.services.impl;

import com.example.demo.data.models.Role;
import com.example.demo.data.services.IRoleSvc;
import com.example.demo.data.services.IUserSvc;
import com.example.demo.data.models.User;
import com.example.demo.security.RoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Component
public class LoginService implements UserDetailsService, UserDetailsPasswordService {

    @Inject
    IUserSvc userSvc;

    @Inject
    IRoleSvc roleSvc;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        org.springframework.security.core.userdetails.User userdetails = null;

        // get the user from the database
        Optional<User> user = userSvc.findByUserName(username);

        User u = null;
        if( user.isPresent() ){
            u = user.get();
        }
        // get the role for the user from the database
        Role role = roleSvc.findByUserName( u.getUserName());

        // build the userdetails object.
        if( user != null  ){
            userdetails = new org.springframework.security.core.userdetails.User( u.getUserName(),
                    u.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    getGrantedAuthorities( Arrays.asList(role.getRole()) ));
        }else{
            throw new UsernameNotFoundException("User not found:  " + username);
        }

        return userdetails;
    }

//    private Collection<? extends GrantedAuthority> getAuthorities( Collection<Role> roles) {
//        return getGrantedAuthorities( getGrantedAuthorities(roles) );
//    }

//    private List<String> getPrivileges(Collection<Role> roles) {
//
//        List<String> privileges = new ArrayList<>();
//
//        List<Privilege> collection = new ArrayList<>();
//
//        for (Role role : roles) {
//            collection.addAll(role.getPrivileges());
//        }
//        for (Privilege item : collection) {
//            privileges.add(item.getName());
//        }
//        return privileges;
//    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    @Transactional
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

    @Transactional
    public User registerUser( User user ){

        Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
        user.setCreatedTime( currentTime );
        user.setCreatedBy("web-registration");
        user.setUpdatedTime(currentTime);

        // first save the user
        User user1 = userSvc.saveAndFlush(user);

        // next save the role. Default role is "User"
        Role role = new Role();
        role.setRole(RoleEnum.ROLE_USER.name());
        role.setUserName(user1.getUserName());
        role.setCreatedBy("Web");
        role.setCreatedTime(currentTime);
        role.setUpdatedTime(currentTime);

        roleSvc.saveAndFlush( role );

        return user1;
    }

    public boolean isUserIdInUse( String userId ){
        boolean result = false;

        if( userSvc.findByUserName(userId) != null ){
            result = true;
        }

        return result;
    }

    public boolean isEmilInUse( String email ){
        boolean result = false;

        if( userSvc.findByEmail(email) != null ){
            result = true;
        }

        return result;
    }

}
