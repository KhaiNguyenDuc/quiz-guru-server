package com.quizguru.auth.service.impl;

import com.quizguru.auth.dto.request.EmailRequest;
import com.quizguru.auth.dto.request.ResetPasswordRequest;
import com.quizguru.auth.exception.InvalidRequestException;
import com.quizguru.auth.exception.ResourceNotFoundException;
import com.quizguru.auth.model.PasswordResetToken;
import com.quizguru.auth.model.User;
import com.quizguru.auth.producer.MailProducer;
import com.quizguru.auth.producer.PasswordResetTokenProperties;
import com.quizguru.auth.repository.PasswordResetTokenRepository;
import com.quizguru.auth.repository.UserRepository;
import com.quizguru.auth.service.PasswordResetService;
import com.quizguru.auth.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final PasswordEncoder encoder;
    private final MailProducer mailProducer;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordResetTokenProperties passwordResetTokenProperties;

    @Override
    public String sendResetPassword(String username) {

        Optional<User> userOpt;
        if(username.contains("@")){
            userOpt = userRepository.findByEmail(username);
        }else{
            userOpt = userRepository.findByUsername(username);
        }

        if(userOpt.isEmpty()){
            throw new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_USERNAME_NOT_EXIST, username);
        }

        User user = userOpt.get();
        PasswordResetToken token;
        Optional<PasswordResetToken> tokenOpt = passwordResetTokenRepository.findByUser(user);

        token = tokenOpt.orElseGet(PasswordResetToken::new);
        token.setUser(user);
        token.setToken(RandomStringUtils.randomNumeric(4));
        token.setExpiredDate(Instant.now().plusMillis(passwordResetTokenProperties.getExpirationMs()));
        passwordResetTokenRepository.save(token);

        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .subject(Constant.PASSWORD_RESET_SUBJECT)
                .userId(user.getId())
                .body(token.getToken())
                .build();

        mailProducer.sendResetPasswordMail(emailRequest);
        return user.getId();
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        if(request.newPassword().length() < 7){
            throw new InvalidRequestException(Constant.ERROR_CODE.INVALID_PASSWORD_FORMAT, "has more than 7 characters");
        }
        Optional<User> userOpt = userRepository.findByEmail(request.email());
        if(userOpt.isEmpty()){
            throw new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_USERNAME_NOT_EXIST, request.email());
        }
        User user = userOpt.get();

        Optional<PasswordResetToken> tokenOpt = passwordResetTokenRepository.findByUser(user);
        if(tokenOpt.isEmpty()) {
            throw new ResourceNotFoundException(Constant.ERROR_CODE.PASSWORD_REST_TOKEN_NOT_FOUND, request.email());
        }

        PasswordResetToken token = tokenOpt.get();

        if(request.token().equals(token.getToken()) && token.getExpiredDate().compareTo(Instant.now()) > 0){
            user.setPassword(encoder.encode(request.newPassword()));
        }else{
            throw new InvalidRequestException(Constant.ERROR_CODE.PASSWORD_RESET_TOKEN_EXPIRED, request.token());
        }
        userRepository.save(user);

        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .subject(Constant.PASSWORD_RESET_SUCCESS_SUBJECT)
                .userId(user.getId())
                .body(Constant.PASSWORD_RESET_SUCCESS_SUBJECT)
                .build();
        mailProducer.sendMail(emailRequest);
    }
}
