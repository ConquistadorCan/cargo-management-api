package com.mfc.logistics.cargo_management_api.controller;

import com.mfc.logistics.cargo_management_api.dto.request.UserRegisterRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.response.UserRegisterResponseDTO;
import com.mfc.logistics.cargo_management_api.enums.UserRoleEnum;
import com.mfc.logistics.cargo_management_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-staff")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserRegisterResponseDTO> createStaff(@RequestBody UserRegisterRequestDTO request) {
        UserRegisterResponseDTO response = userService.userReg(request, UserRoleEnum.STAFF);
        return ResponseEntity.ok(response);
    }

}
