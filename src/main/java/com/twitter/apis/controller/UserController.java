package com.twitter.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.apis.DTO.UserDTO;
import com.twitter.apis.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDTO userDTO){
        ResponseEntity<Object> responseEntity = userService.createUser(userDTO);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserDTO userDTO) {
        ResponseEntity<Object> responseEntity = userService.login(userDTO);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    // @GetMapping("/account/{id}")
    // public ResponseEntity<Object> getUserById(@PathVariable Long id) throws UserNotFoundException {
    //     UserDTO userDTO = userService.getUserById(id);
    //     return ResponseEntity.ok(userDTO);
    // }

}
