package com.mfc.logistics.cargo_management_api.controller;

import com.mfc.logistics.cargo_management_api.dto.request.UserRegisterRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.response.UserLoginResponseDTO;
import com.mfc.logistics.cargo_management_api.dto.response.UserRegisterResponseDTO;
import com.mfc.logistics.cargo_management_api.dto.request.UserLoginRequestDTO;
import com.mfc.logistics.cargo_management_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> createUser(@RequestBody UserRegisterRequestDTO request) {
        UserRegisterResponseDTO response = userService.userReg(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO request) {
        UserLoginResponseDTO response = userService.userLogin(request);
        return ResponseEntity.ok(response);
    }
}
