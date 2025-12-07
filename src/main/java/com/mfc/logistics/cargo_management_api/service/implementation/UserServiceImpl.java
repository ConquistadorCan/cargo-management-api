package com.mfc.logistics.cargo_management_api.service.implementation;

import com.mfc.logistics.cargo_management_api.dto.request.UserLoginRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.request.UserRegisterRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.response.UserLoginResponseDTO;
import com.mfc.logistics.cargo_management_api.dto.response.UserRegisterResponseDTO;
import com.mfc.logistics.cargo_management_api.enums.UserRoleEnum;
import com.mfc.logistics.cargo_management_api.model.User;
import com.mfc.logistics.cargo_management_api.repository.UserRepository;
import com.mfc.logistics.cargo_management_api.service.JwtService;
import com.mfc.logistics.cargo_management_api.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserRegisterResponseDTO userReg(UserRegisterRequestDTO request) {
        return userReg(request, UserRoleEnum.CUSTOMER);
    }

    @Override
    @Transactional
    public UserRegisterResponseDTO userReg(UserRegisterRequestDTO request, UserRoleEnum role) {
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("User already exists with username: " + request.getUsername());
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                encodedPassword,
                role
        );
        userRepository.save(user);
        return UserRegisterResponseDTO.from(user);
    }

    @Override
    public UserLoginResponseDTO userLogin(UserLoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user =  userRepository.findByUsername(request.getUsername()).orElseThrow();
        UserDetails userDetails = new UserDetailsImpl(user);

        var jwtToken = jwtService.generateToken(userDetails);

        return UserLoginResponseDTO.from(user, jwtToken);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public List<User> findAvailableStaff() {
        return userRepository.findAvailableStaff();
    }
}
