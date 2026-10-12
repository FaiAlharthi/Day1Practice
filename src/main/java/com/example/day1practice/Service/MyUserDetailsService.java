package com.example.day1practice.Service;

import com.example.day1practice.Api.ApiException;
import com.example.day1practice.Model.User;
import com.example.day1practice.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// نسوي لود لليوزر
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;

    //امرر لهذي الميثود الشي الي بدخل منه authentication
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = authRepository.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("wrong username or password");
        }
        return user;
    }
}
