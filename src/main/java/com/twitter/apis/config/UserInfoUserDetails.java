package com.twitter.apis.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfoUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(Long userid, String userInfoUsername, String userInfoPassword,
            String userInfoAuthorities) {
        this.id = userid;
        this.username = userInfoUsername;
        this.password = userInfoPassword;
        this.authorities = List.of(() -> userInfoAuthorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

}
