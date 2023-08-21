package com.example.lifipav2.security.service;

import com.example.lifipav2.model.UserEntity;
import com.example.lifipav2.repository.UserEntityRepository;
import com.example.lifipav2.security.entity.UserEntityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserEntityUserDetailsService implements UserDetailsService {
    @Autowired
    private  UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userEntityRepository.findByUsername(username);
        UserEntityUserDetails userDetails = userEntity.map(UserEntityUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        userDetails.getAuthorities().stream().forEach(grantedAuthority -> System.out.println(grantedAuthority.getAuthority()));
        return userDetails;
    }
}
