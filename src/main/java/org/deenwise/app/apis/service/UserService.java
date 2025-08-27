package org.deenwise.app.apis.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.deenwise.app.apis.dto.UserDTO;
import org.deenwise.app.apis.dto.UserRole;
import org.deenwise.app.apis.exception.UserNotFoundException;
import org.deenwise.app.apis.mapper.UserMapper;
import org.deenwise.app.apis.model.UserModel;
import org.deenwise.app.apis.repository.UserRepository;
import org.deenwise.app.apis.request.ResetPasswordRequest;
import org.deenwise.app.apis.response.ResetPasswordResponse;
import org.deenwise.app.apis.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserDTO userDTO, UserRole role) {
        UserModel userModel = userMapper.convertToModel(userDTO);
        userModel.setUserRole(role);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(userModel);
        log.info("user details saved to db..");
        return UserResponse.builder()
                .email(userDTO.getEmail())
                .fullname(userDTO.getFullname())
                .id(userModel.getId())
                .role(userModel.getUserRole().toString())
                .build();
    }

    public UserDTO getUserByEmail(String email) {
        return userMapper.convertToDTO(userRepository.findByEmail(email).orElse(null));
    }

    public UserRole getUserRole(String email) {
        return userRepository.findByEmail(email).
                orElseThrow(() -> new UserNotFoundException("User Data Not Found")).getUserRole();
    }

    public UserDTO getUserByID(Long id) {
        return userMapper.convertToDTO(userRepository.findById(id).orElseThrow(()
                -> new UserNotFoundException("User Data Not found")));
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::convertToDTO)
                .toList();
    }

    public ResetPasswordResponse generatePasswordResetLink(String currentlyLoggedInUserEmail, String email, HttpServletRequest httpServletRequest) {
        String resetLink = "http://localhost:8080//deen/api/user/password/reset/link/";
        String resetToken = null;
        ResetPasswordResponse resetPasswordResponse = null;
        if(Objects.equals(currentlyLoggedInUserEmail,email)) {
            HttpSession httpSession = httpServletRequest.getSession();
            resetToken = UUID.randomUUID().toString();
            resetLink = resetLink.concat(resetToken);

            //send email to user.....


            resetPasswordResponse = ResetPasswordResponse.builder()
                    .email(email)
                    .passwordReset(false)
                    .mailSent(true)
                    .resetLink(resetLink)
                    .resetToken(resetToken)
                    .build();

            httpSession.setAttribute("resetResponse",resetPasswordResponse);

        }
        return resetPasswordResponse;
    }

    public ResetPasswordResponse resetPassword(ResetPasswordResponse resetPasswordResponse, String resetToken, ResetPasswordRequest resetPasswordRequest) {
        if(Objects.equals(resetToken,resetPasswordResponse.getResetToken())) {
            UserModel userModel = userRepository.findByEmail(resetPasswordResponse.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User Data Not Found"));
            if(passwordEncoder.matches(resetPasswordRequest.getOldPassword(),userModel.getPassword())) {
                userModel.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
                log.info("password reset successfully");
                resetPasswordResponse.setPasswordReset(true);
            }
        }
        return resetPasswordResponse;
    }
}
