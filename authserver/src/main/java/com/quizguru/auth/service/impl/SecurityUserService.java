package com.quizguru.auth.service.impl;

import com.quizguru.auth.exception.AccessDeniedException;
import com.quizguru.auth.exception.UnauthorizedException;
import com.quizguru.auth.model.UserPrincipal;
import com.quizguru.auth.model.User;
import com.quizguru.auth.repository.UserRepository;
import com.quizguru.auth.service.UserService;
import com.quizguru.auth.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UnauthorizedException(Constant.ERROR_CODE.UNAUTHORIZED_USERNAME_NOT_EXIST, username));

        if(!user.isEnabled()){
            throw new AccessDeniedException(Constant.ERROR_CODE.USER_NOT_ENABLED, username);
        }

        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UnauthorizedException(Constant.ERROR_CODE.UNAUTHORIZED_ID_NOT_EXIST, id));

        if(!user.isEnabled()){
            throw new AccessDeniedException(Constant.ERROR_CODE.USER_NOT_ENABLED, id);
        }

        return UserPrincipal.create(user);
    }
}
