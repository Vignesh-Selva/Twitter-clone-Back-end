package com.twitter.apis.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.twitter.apis.model.UserEntity;
import com.twitter.apis.repository.UserRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userInfo = repository.findByUsername(username);
        Long userInfoId = userInfo.get().getId();
        String userInfoUsername = userInfo.get().getUsername();
        String userInfoPassword = userInfo.get().getPassword();
        String userInfoAuthorities = userInfo.get().getRoles();

        return new UserInfoUserDetails(userInfoId, userInfoUsername, userInfoPassword, userInfoAuthorities);

    }
}