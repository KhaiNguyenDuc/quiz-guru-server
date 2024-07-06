package com.quizguru.auth.service.impl;

import com.quizguru.auth.exception.UnAuthorizeException;
import com.quizguru.auth.model.UserPrincipal;
import com.quizguru.auth.model.User;
import com.quizguru.auth.repository.UserRepository;
import com.quizguru.auth.service.UserService;
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
                .orElseThrow(() -> new UnAuthorizeException("Username not exist"));

        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UnAuthorizeException("UserId not exits"));

        return UserPrincipal.create(user);
    }
}
