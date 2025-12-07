package com.mfc.logistics.cargo_management_api.dto.response;

import com.mfc.logistics.cargo_management_api.enums.UserRoleEnum;
import com.mfc.logistics.cargo_management_api.model.User;

public class UserRegisterResponseDTO {
    private String username;
    private String email;
    private UserRoleEnum role;

    private UserRegisterResponseDTO() {}

    public static UserRegisterResponseDTO from(User user) {
        UserRegisterResponseDTO dto = new UserRegisterResponseDTO();
        dto.username = user.getUsername();
        dto.email = user.getEmail();
        dto.role = user.getRole();
        return dto;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role.name(); }
}
