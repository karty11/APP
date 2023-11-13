package com.keystoneconstructs.credentia.model;

import lombok.Data;

@Data
public class LoginResponse {

    private UserResponse userResponse;

    private String token;

}
