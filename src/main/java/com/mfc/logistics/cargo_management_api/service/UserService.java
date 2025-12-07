package com.mfc.logistics.cargo_management_api.service;

import com.mfc.logistics.cargo_management_api.dto.request.UserLoginRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.request.UserRegisterRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.response.UserLoginResponseDTO;
import com.mfc.logistics.cargo_management_api.dto.response.UserRegisterResponseDTO;
import com.mfc.logistics.cargo_management_api.enums.UserRoleEnum;

public interface UserService {
    public UserRegisterResponseDTO userReg(UserRegisterRequestDTO request);
    public UserRegisterResponseDTO userReg(UserRegisterRequestDTO request, UserRoleEnum role);
    public UserLoginResponseDTO userLogin(UserLoginRequestDTO request);
}
