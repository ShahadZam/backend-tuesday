package com.example.rentaperson.service;

import com.example.rentaperson.model.User;
import com.example.rentaperson.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findUsersByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("Wrong username or password");
        }

        return user;

    }
}
