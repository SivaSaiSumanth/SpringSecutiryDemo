package com.springsecurity.securitydemo.repository;


import com.springsecurity.securitydemo.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAuthorityRepository
        extends JpaRepository<UserAuthority, Long> {

    List<UserAuthority> findByUserId(Long userId);
}