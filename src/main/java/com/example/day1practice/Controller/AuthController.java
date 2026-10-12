package com.example.day1practice.Controller;

import com.example.day1practice.Api.ApiResponse;
import com.example.day1practice.Model.User;
import com.example.day1practice.Service.AuthService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private  final AuthService authService;

    // @authenticationPrincipal

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user){
        authService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("new user created"));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable Integer id){
        authService.deleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("user deleted"));
    }

}
