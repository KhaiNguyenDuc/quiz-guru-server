package com.quizguru.quizzes.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StompConnectAuthInterceptor implements ChannelInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(StompConnectAuthInterceptor.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtDecoder jwtDecoder;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null || accessor.getCommand() != StompCommand.CONNECT) {
            return message;
        }

        String rawAuthorization = readAuthorizationHeader(accessor);
        if (rawAuthorization == null || rawAuthorization.isBlank()) {
            throw new IllegalArgumentException("Missing Authorization header in STOMP CONNECT");
        }

        String token = extractBearerToken(rawAuthorization);
        try {
            Jwt jwt = jwtDecoder.decode(token);
            String userId = jwt.getSubject();

            Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
            if (sessionAttributes != null) {
                sessionAttributes.put("user", userId);
            }
            accessor.setUser(new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES));
        } catch (JwtException ex) {
            LOGGER.warn("Invalid token in STOMP CONNECT: {}", ex.getMessage());
            throw new IllegalArgumentException("Invalid Authorization token", ex);
        }

        return message;
    }

    private String readAuthorizationHeader(StompHeaderAccessor accessor) {
        String authorization = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
        if (authorization == null) {
            authorization = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER.toLowerCase());
        }
        return authorization;
    }

    private String extractBearerToken(String rawAuthorization) {
        if (rawAuthorization.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length())) {
            return rawAuthorization.substring(BEARER_PREFIX.length()).trim();
        }
        return rawAuthorization.trim();
    }
}
