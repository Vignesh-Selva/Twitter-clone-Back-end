package com.twitter.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.apis.DTO.UserProfileDTO;
import com.twitter.apis.service.UserProfileService;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Long id) {
        return userProfileService.getUserProfile(id);
    }

    @PreAuthorize("authentication.principal.id == #id")
    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> updateUserProfile(@PathVariable Long id,
            @RequestBody UserProfileDTO userProfileDTO) {
        return userProfileService.updateUserProfile(id, userProfileDTO);
    }

}
