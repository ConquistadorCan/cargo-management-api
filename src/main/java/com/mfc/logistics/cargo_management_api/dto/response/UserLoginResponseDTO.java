package com.mfc.logistics.cargo_management_api.dto.response;

import com.mfc.logistics.cargo_management_api.model.User;

public class UserLoginResponseDTO {
    private String username;
    private String token;

    public UserLoginResponseDTO() {}

    public static UserLoginResponseDTO from(User user, String token) {
        UserLoginResponseDTO dto = new UserLoginResponseDTO();
        dto.username = user.getUsername();
        dto.token = token;
        return dto;
    }

    public String getUsername() { return username; }
    public String getToken() { return token; }

}
