package com.twitter.apis.components;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.twitter.apis.config.UserInfoUserDetails;

@Component
public class UserAuthenticationUtility {
        public Long getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
            return userDetails.getId();
        }
        throw new RuntimeException("User not authenticated.");
    }
}
