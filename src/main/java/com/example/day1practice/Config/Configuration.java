package com.example.day1practice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;

@org.springframework.context.annotation.Configuration //عشان يعترف فيه
@EnableWebSecurity // هذي تسمح بالسيكورتي على الويب
public class Configuration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //الحين بنسوي الفيلتر تشين
    //Http يتحقق من الوصول ، دايم المثود ذي ترمي اكسبشن
    // الميثود ترجع سلسلة فلاتر، كل سلسلة مسؤلة عن شي معيّن
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        //.csrf اول سلسلة، هي هجمة
        //authorizeHttp , هذي سلسلة توزع الصلاحيات  , اقول الصفحة الفلانية يدخل عليها الي رول حقه الشي الفلاني ،
        // authorizeHttp لازم يكون عندي ريكويست ماتشر لكل إند بوينت
        //  401 غير مصرّح لك
        // 403 هنا فوربيدن، يعني تطلب باث مو لك
        // PermitAll كل شي مفتوح للكل الصفخة
        // ممنوع يتكرر باث مرتين authorizeHttp
        // hasAuthority and hasRole , same thing  , كلها نمرر لها الرول
        // hasAnyAuth يعني اكثر من رول اقدر امرر
        // anyRequest يعني اي ريكويست مو موجود فوق، نخليه اوثينتيكيتد عشان يمنعه من الدخول يعني لازم يتحقق انا ميين ؟
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/user/register").permitAll()
                        .requestMatchers("/api/v1/user/deleteUser/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/todo/getAllTodos").hasAnyAuthority("ADMIN")
                        .requestMatchers("/api/v1/todo/getMyTodos","/api/v1/todo/create","/api/v1/todo/deleteTodo/**","/api/v1/todo/updateTodo/**").hasAnyAuthority("USER")
                        .anyRequest().authenticated()
            )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/user/auth/logout")
                        .invalidateHttpSession(true)
                )
                .httpBasic(basic -> {})
                .build();

    }
}
