package com.quizguru.auth.service.impl;

import com.quizguru.auth.dto.request.EmailRequest;
import com.quizguru.auth.dto.response.RegisterResponse;
import com.quizguru.auth.producer.MailProducer;
import com.quizguru.auth.properties.VerificationTokenProperties;
import com.quizguru.auth.exception.InvalidVerifyTokenException;
import com.quizguru.auth.exception.ResourceNotFoundException;
import com.quizguru.auth.model.User;
import com.quizguru.auth.model.VerificationToken;
import com.quizguru.auth.repository.UserRepository;
import com.quizguru.auth.repository.VerificationTokenRepository;
import com.quizguru.auth.service.VerificationTokenService;
import com.quizguru.auth.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final VerificationTokenProperties verificationTokenProperties;
    private final MailProducer mailProducer;
    @Override
    public Boolean verifyUser(String token, String username) {
        Optional<User> userOpt;
        if(username.contains("@")){
            userOpt = userRepository.findByEmail(username);
        }else{
            userOpt = userRepository.findByUsername(username);
        }

        if(userOpt.isEmpty()){
            throw new InvalidVerifyTokenException(Constant.ERROR_CODE.UNAUTHORIZED_USERNAME_NOT_EXIST, username);
        }

        User user = userOpt.get();

        Optional<VerificationToken> tokenOpt = verificationTokenRepository.findByUser(user);
        if(tokenOpt.isEmpty()){
            throw new InvalidVerifyTokenException(Constant.ERROR_CODE.VERIFY_TOKEN_NOT_FOUND, token);
        }
        VerificationToken verificationToken = tokenOpt.get();
        if (verificationToken.getExpiredDate().compareTo(Instant.now()) < 0) {
            throw new InvalidVerifyTokenException(Constant.ERROR_CODE.VERIFY_TOKEN_EXPIRED, token);
        }

        if(verificationToken.getToken().equals(token)){
            user.setIsEnable(true);
            userRepository.save(user);
            return true;
        }
        throw new InvalidVerifyTokenException(Constant.ERROR_CODE.INVALID_REFRESH_TOKEN, token);
    }

    @Override
    public Boolean resendVerifyToken(String username) {

        Optional<User> userOtp;
        if(username.contains("@")){
            userOtp = userRepository.findByEmail(username);
        }else{
            userOtp = userRepository.findByUsername(username);
        }

        if(userOtp.isEmpty()){
            throw new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_USERNAME_NOT_EXIST, username);
        }
        User user = userOtp.get();
        Optional<VerificationToken> token = verificationTokenRepository.findByUser(user);
        if(token.isEmpty()){
            throw new InvalidVerifyTokenException(Constant.ERROR_CODE.VERIFY_TOKEN_NOT_FOUND, username);
        }

        VerificationToken verificationToken = token.get();
        verificationToken.setToken(RandomStringUtils.randomNumeric(4));
        verificationToken.setExpiredDate(Instant.now().plusMillis(verificationTokenProperties.getExpirationMs()));
        verificationTokenRepository.save(verificationToken);

        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .subject(Constant.VERIFY_SUBJECT)
                .userId(user.getId())
                .body(verificationToken.getToken())
                .build();

        mailProducer.sendVerifyMail(emailRequest);
        return true;
    }

    @Override
    public VerificationToken createToken(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidVerifyTokenException(Constant.ERROR_CODE.UNAUTHORIZED_ID_NOT_EXIST, userId));

        final String token = RandomStringUtils.randomNumeric(4);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiredDate(Instant.now().plusMillis(verificationTokenProperties.getExpirationMs()));
        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    public void sendVerifyToken(RegisterResponse registerResponse, VerificationToken verificationToken) {
        EmailRequest emailRequest = EmailRequest.builder()
                .to(registerResponse.email())
                .subject(Constant.VERIFY_SUBJECT)
                .userId(registerResponse.id())
                .body(verificationToken.getToken())
                .build();

        mailProducer.sendVerifyMail(emailRequest);
    }

}
