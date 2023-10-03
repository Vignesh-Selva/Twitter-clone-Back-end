package com.twitter.apis.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.twitter.apis.DTO.UserDTO;
import com.twitter.apis.exceptions.UserNotFoundException;
import com.twitter.apis.exceptions.UserRegistrationException;
import com.twitter.apis.model.UserEntity;
import com.twitter.apis.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register user and save to database
    public ResponseEntity<Object> createUser(UserDTO userDTO) {

        try {

            // Check if user already exists
            if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("User already exists with username: " + userDTO.getUsername());
            }

            // check if email already exists
            if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("User already exists with email: " + userDTO.getEmail());
            }

            // Hash Password
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            userDTO.setPassword(encodedPassword);

            // Save user
            UserEntity userEntity = convertToEntity(userDTO);
            userEntity.setBio("Tell something about yourself");
            userEntity.setProfilePicture("https://i.imgur.com/3GvWXDg.png");
            userRepository.save(userEntity);

            UserEntity savedEntity = userRepository.save(userEntity);
            if (savedEntity != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
            }
            throw new UserRegistrationException("Error registering user");
        } catch (UserRegistrationException ex) {
            throw ex;
        }
    }

    // Login user
    public ResponseEntity<Object> login(@Valid UserDTO userDTO) {

        try {
            // check if user exists
            Optional<UserEntity> userEntity = userRepository.findByUsername(userDTO.getUsername());
            if (!userEntity.isPresent()) {
                throw new UserNotFoundException("User not found with username: " + userDTO.getUsername());
            }

            // check if encoded password matches
            if (!passwordEncoder.matches(userDTO.getPassword(), userEntity.get().getPassword())) {
                throw new UserRegistrationException("Invalid password");
            }

            return ResponseEntity.ok("Login successful");
        } catch (Exception ex) {
            throw ex;
        }
    }

    // Other service helper methods

    private UserEntity convertToEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setRoles(userDTO.getRoles() != null ? userDTO.getRoles() : "ROLE_USER");
        return userEntity;
    }

    private UserDTO convertToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setRoles(userEntity.getRoles());
        return userDTO;
    }

    public UserDTO getUserByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            return convertToDTO(userEntity.get());
        }
        throw new UserNotFoundException("User not found with username: " + username);
    }

    // public UserDTO getUserById(Long id) throws UserNotFoundException {
    // Optional<UserEntity> userEntity = userRepository.findById(id);
    // if (userEntity.isPresent()) {
    // return convertToDTO(userEntity.get());
    // }
    // throw new UserNotFoundException("User not found with id: " + id);
    // }
}
