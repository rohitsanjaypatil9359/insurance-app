package com.insurance.insurance_app.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
