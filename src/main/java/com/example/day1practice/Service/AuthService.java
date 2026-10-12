package com.example.day1practice.Service;

import com.example.day1practice.Api.ApiException;
import com.example.day1practice.Model.User;
import com.example.day1practice.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository userRepo;

    public void register (User user){
        user.setRole("USER");
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        userRepo.save(user);
    }

    public void deleteUser (Integer id){
        User user = userRepo.findUserById(id);
        if(user == null){
            throw new ApiException("No user found");
        }
        if(user.getRole().equalsIgnoreCase("USER")){
            userRepo.delete(user);
            return;
        }
        throw new ApiException("you cannot delete another admin account");
    }
}
