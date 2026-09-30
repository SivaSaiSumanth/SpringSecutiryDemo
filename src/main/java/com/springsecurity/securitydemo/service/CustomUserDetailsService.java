package com.springsecurity.securitydemo.service;


import com.springsecurity.securitydemo.entity.AppUser;
import com.springsecurity.securitydemo.entity.UserAuthority;
import com.springsecurity.securitydemo.repository.UserAuthorityRepository;
import com.springsecurity.securitydemo.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    public CustomUserDetailsService(
            UserRepository userRepository,
            UserAuthorityRepository userAuthorityRepository) {

        this.userRepository = userRepository;
        this.userAuthorityRepository = userAuthorityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username
                        )
                );

        List<UserAuthority> userAuthorities =
                userAuthorityRepository.findByUserId(appUser.getId());

        List<GrantedAuthority> authorities =
                userAuthorities.stream()
                        .map(userAuthority ->
                                (GrantedAuthority) new SimpleGrantedAuthority(
                                        userAuthority.getAuthority()
                                )
                        )
                        .collect(Collectors.toList());

        authorities.add(
                new SimpleGrantedAuthority(
                        "ROLE_" + appUser.getRole()
                )
        );

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(authorities)
                .build();
    }
}