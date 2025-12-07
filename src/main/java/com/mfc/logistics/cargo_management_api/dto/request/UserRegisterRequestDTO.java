package com.mfc.logistics.cargo_management_api.dto.request;

public class UserRegisterRequestDTO {
    private String username;
    private String email;
    private String password;

    public String getUsername() { return this.username; }
    public String getEmail() { return this.email; }
    public String getPassword() { return this.password; }
}
