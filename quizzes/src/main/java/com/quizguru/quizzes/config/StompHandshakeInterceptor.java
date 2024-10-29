package com.quizguru.quizzes.config;

import com.quizguru.quizzes.utils.KeycloakRoleConverter;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.rotation.AdapterTokenVerifier;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandshakeInterceptor implements HandshakeInterceptor {

    private final KeycloakSpringBootProperties configuration;
    private final KeycloakRoleConverter keycloakRoleConverter;

    @Override
    public boolean beforeHandshake(ServerHttpRequest req, ServerHttpResponse resp, WebSocketHandler h, Map<String, Object> atts) {
        List<String> protocols = req.getHeaders().get("Sec-WebSocket-Protocol");
        try {
            String token = protocols.get(0).split(", ")[2];
            log.debug("Token: " + token);
            AccessToken accessToken = AdapterTokenVerifier.verifyToken(token, KeycloakDeploymentBuilder.build(configuration));

            resp.setStatusCode(HttpStatus.SWITCHING_PROTOCOLS);
            log.debug("token valid");
//            String keycloakUrl = configuration.getAuthServerUrl(); // Get Keycloak server URL
//            String realmName = configuration.getRealm(); // Get the realm name
//            String jwkSetUri = keycloakUrl + "/realms/" + realmName + "/protocol/openid-connect/certs";
//            JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
//            Jwt jwt = jwtDecoder.decode(token);
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    accessToken.getSubject(),
//                    null,
//                    keycloakRoleConverter.convert(jwt)
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            atts.put("user", accessToken.getSubject());

        } catch (IndexOutOfBoundsException e) {
            resp.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }
        catch (VerificationException e) {
            resp.setStatusCode(HttpStatus.FORBIDDEN);
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest rq, ServerHttpResponse rp, WebSocketHandler h, @Nullable Exception e) {}
}