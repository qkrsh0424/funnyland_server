package com.funnyland.funnyland_server.model.user.repository;

import java.util.Optional;

import com.funnyland.funnyland_server.model.user.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, String>{
    
    @Query(value = "SELECT * FROM user WHERE email=:email AND deleted=:isDeleted", nativeQuery=true)
    public Optional<UserEntity> findByEmail_Custom(String email, int isDeleted);

    @Query(value = "SELECT * FROM user WHERE username=:username AND deleted=:isDeleted", nativeQuery=true)
    public Optional<UserEntity> findByUsername_Custom(String username, int isDeleted);

    Optional<UserEntity> findByUsername(String username);
}
