package com.twitter.apis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.apis.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(Long id);

}
