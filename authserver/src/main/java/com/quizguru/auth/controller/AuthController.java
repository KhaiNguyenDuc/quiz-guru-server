package com.quizguru.auth.controller;

import com.quizguru.auth.dto.request.*;
import com.quizguru.auth.dto.response.*;
import com.quizguru.auth.model.UserPrincipal;
import com.quizguru.auth.model.VerificationToken;
import com.quizguru.auth.service.AuthService;
import com.quizguru.auth.service.PasswordResetService;
import com.quizguru.auth.service.RefreshTokenService;
import com.quizguru.auth.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final VerificationTokenService verificationTokenService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/validate-token")
    public ResponseEntity<ApiResponse> validateToken(@RequestBody String token){
        TokenValidationResponse result = authService.validateJwtToken(token);
        ApiResponse response = new ApiResponse(result, "Success");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody LoginCredentials loginCredentials){
        TokenResponse result = authService.authenticate(loginCredentials);
        ApiResponse response = new ApiResponse(result, "Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse> refreshToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest
            ){
        TokenResponse tokenResponse = refreshTokenService.renewAccessToken(refreshTokenRequest);
        ApiResponse response = new ApiResponse(tokenResponse, "Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterCredentials registerCredentials){
        RegisterResponse result = authService.register(registerCredentials);
        VerificationToken token = verificationTokenService.createToken(result.id());
        verificationTokenService.sendVerifyToken(result, token);
        ApiResponse response = new ApiResponse(result, "Success");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse> verifyUser(
            @RequestBody VerifyRequest verifyRequest
    ){
        Boolean result = verificationTokenService.verifyUser(verifyRequest.token(), verifyRequest.username());
        ApiResponse response = new ApiResponse(result, "Success");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/send-verify")
    public ResponseEntity<ApiResponse> resendVerifyToken(
            @RequestParam String username

    ){
        Boolean result = verificationTokenService.resendVerifyToken(username);
        ApiResponse response = new ApiResponse(result, "Success");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/send-reset-password")
    public ResponseEntity<ApiResponse> sendResetPassword(
            @RequestParam("username") String username)  {

        String userId = passwordResetService.sendResetPassword(username);
        ApiResponse response = new ApiResponse(userId, "Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestBody ResetPasswordRequest resetPasswordRequest)  {

        passwordResetService.resetPassword(resetPasswordRequest);
        ApiResponse response = new ApiResponse("OK", "Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/current")
    public ResponseEntity<ApiResponse> findCurrentUser(
            @CurrentSecurityContext(expression="authentication.principal") UserPrincipal userPrincipal ){
        UserResponse userResponse = authService.findById(userPrincipal.getId());

        ApiResponse response = new ApiResponse(userResponse, "Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/internal/users/role")
    public ResponseEntity<ApiResponse> findRoleFromUserId(@RequestParam("userId") String userId){
        String roleName = authService.findRoleFromUserId(userId);
        log.info("start findRoleFromUserId");
        ApiResponse response = new ApiResponse(roleName, "Success");
        log.info("end findRoleFromUserId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
