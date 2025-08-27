package org.deenwise.app.apis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.deenwise.app.apis.dto.UserDTO;
import org.deenwise.app.apis.dto.UserRole;
import org.deenwise.app.apis.request.LoginRequest;
import org.deenwise.app.apis.request.RefreshTokenRequest;
import org.deenwise.app.apis.request.ResetPasswordRequest;
import org.deenwise.app.apis.response.AuthResponse;
import org.deenwise.app.apis.response.ResetPasswordResponse;
import org.deenwise.app.apis.response.UserResponse;
import org.deenwise.app.apis.service.JwtService;
import org.deenwise.app.apis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("/deen/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
        System.out.println("authenticating...");
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO, UserRole.STUDENT), HttpStatus.OK);
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<AuthResponse> getAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        System.out.println(refreshTokenRequest);
        return new ResponseEntity<>(jwtService.revalidateAccessToken(refreshTokenRequest),HttpStatus.OK);
    }

    @GetMapping("/token/info")
    public ResponseEntity<AuthResponse> getTokenInfo() {
        return null;
    }


    @PostMapping("/password/reset")
    public ResponseEntity<ResetPasswordResponse> resetUserPassword(@RequestBody String email, HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        if(header != null) {
            String token = header.substring(7);
            String currentlyLoggedInUserEmail = jwtService.extractEmail(token);
            return new ResponseEntity<>(userService.
                    generatePasswordResetLink(currentlyLoggedInUserEmail,email,httpServletRequest),
                    HttpStatus.OK);
        }

        return null;
    }

    @GetMapping("/password/reset/link/{resetToken}")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@PathVariable String resetToken, @RequestBody ResetPasswordRequest resetPasswordRequest, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        ResetPasswordResponse resetPasswordResponse = (ResetPasswordResponse) httpSession.getAttribute("resetResponse");
        if(!Objects.isNull(resetPasswordResponse)) {
            httpSession.removeAttribute("resetResponse");
            return new ResponseEntity<>(userService.resetPassword(resetPasswordResponse,resetToken,resetPasswordRequest),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResetPasswordResponse(),HttpStatus.OK);
    }
}
