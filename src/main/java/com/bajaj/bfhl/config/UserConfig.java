package com.bajaj.bfhl.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class UserConfig {

    @Value("${user.fullName}")
    private String fullName;

    @Value("${user.dob}")
    private String dob;

    @Value("${user.email}")
    private String email;

    @Value("${user.rollNumber}")
    private String rollNumber;

    /**
     * Returns the user ID as fullName_dob (all lowercase).
     */
    public String getUserId() {
        return (fullName + "_" + dob).toLowerCase();
    }
}
