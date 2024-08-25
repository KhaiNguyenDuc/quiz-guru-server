package com.quizguru.libraries.config.audit;


import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

public class CustomAuditAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken))
                .map(Principal::getName);
    }
}
