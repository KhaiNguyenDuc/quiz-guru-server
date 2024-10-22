package com.quizguru.gateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String PREFIX_RESOURCE_ROLE = "ROLE_";
    private static final String CLAIM_REALM_ACCESS = "realm_access";
    private static final String CLAIM_ROLES = "roles";

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Map<String, Collection<String>> realmAccess =  source.getClaim(CLAIM_REALM_ACCESS);
        if(realmAccess != null && !realmAccess.isEmpty()){
            Collection<String> roles = realmAccess.get(CLAIM_ROLES);
            grantedAuthorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(PREFIX_RESOURCE_ROLE + role))
                    .collect(Collectors.toSet());
            log.info(roles.toString());
        }
        return grantedAuthorities;
    }
}
