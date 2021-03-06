package com.tacos.config.security;

import com.tacos.domain.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @description:
 * @author: Kan
 * @date: 2021/3/10 23:38
 */
@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username, passwordEncoder.encode(password),
                fullname, city, state, zip, phone
        );
    }
}
