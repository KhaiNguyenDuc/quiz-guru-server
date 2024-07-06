package com.quizguru.auth.service;

import com.quizguru.auth.model.Role;
import com.quizguru.auth.model.User;
import com.quizguru.auth.model.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
     UserDetails loadUserById(String id);
}
