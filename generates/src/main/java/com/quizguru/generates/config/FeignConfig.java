package com.quizguru.generates.config;

import com.quizguru.generates.utils.CustomHeaders;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.nonNull(authentication)){
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                String userId = userDetails.getUsername();
                String userAuthorities = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

                if (userId != null) {
                    template.header(CustomHeaders.X_USER_ID, userId);
                }
                if (userAuthorities != null) {
                    template.header(CustomHeaders.X_USER_AUTHORITIES, userAuthorities);
                }
            }
        }
    }
}
