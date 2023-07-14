package com.sametech.library_management_system.config.app;

import com.sametech.library_management_system.config.mail.MailConfig;
import com.sametech.library_management_system.config.security.password.encoder.LibraryPasswordEncoder;
import com.sametech.library_management_system.config.security.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
public class AppConfig {
    @Value("${sendinblue.mail.url}")
    private String mailUrl;
    @Value("${sendinblue.api.key}")
    private String mailApiKey;
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;


    @Bean
    public MailConfig mailConfig(){
        return new MailConfig(mailApiKey, mailUrl);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(jwtSecretKey);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SCryptPasswordEncoder sCryptPasswordEncoder(){
//        return new SCryptPasswordEncoder();
//    }


}
