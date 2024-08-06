package com.quizguru.auth.controller;

import com.quizguru.auth.dto.request.LoginCredentials;
import com.quizguru.auth.dto.request.RegisterCredentials;
import com.quizguru.auth.dto.response.*;
import com.quizguru.auth.mapper.TokenMapper;
import com.quizguru.auth.model.RefreshToken;
import com.quizguru.auth.model.UserPrincipal;
import com.quizguru.auth.service.AuthService;
import com.quizguru.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/validate-token")
    public ResponseEntity<ApiResponse<TokenValidationResponse>> validateToken(@RequestBody String token){
        TokenValidationResponse result = authService.validateJwtToken(token);
        ApiResponse<TokenValidationResponse> response = new ApiResponse<>(result, "Success");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<ApiResponse<TokenResponse>> authenticate(@RequestBody LoginCredentials loginCredentials){
        TokenResponse result = authService.authenticate(loginCredentials);
        ApiResponse<TokenResponse> response = new ApiResponse<>(result, "Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal
    ){
        RefreshToken token = refreshTokenService.generateRefreshToken(userPrincipal.getId());
        RefreshTokenResponse result = TokenMapper.tokenToRefreshTokenResponse(token);
        ApiResponse<RefreshTokenResponse> response = new ApiResponse<>(result, "Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterCredentials registerCredentials){
        RegisterResponse result = authService.register(registerCredentials);
        ApiResponse<RegisterResponse> response = new ApiResponse<>(result, "Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
