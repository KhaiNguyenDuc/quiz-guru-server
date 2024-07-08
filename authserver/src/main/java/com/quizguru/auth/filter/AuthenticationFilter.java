package com.quizguru.auth.filter;

import com.quizguru.auth.exception.TokenValidationException;
import com.quizguru.auth.exception.UnauthorizedException;
import com.quizguru.auth.jwt.JwtTokenProvider;
import com.quizguru.auth.model.UserPrincipal;
import com.quizguru.auth.service.impl.SecurityUserService;
import com.quizguru.auth.utils.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final SecurityUserService securityUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = tokenProvider.resolveToken(request);
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                 authenticateToken(token);
            }
        } catch (Exception ex) {
            throw new UnauthorizedException(Constant.ERROR_CODE.UNAUTHORIZED_INTERNAL_ERROR);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateToken(String token) {
        String userId = tokenProvider.getUserIdFromJwt(token);
        UserPrincipal userPrincipal = (UserPrincipal) securityUserService.loadUserById(userId);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userPrincipal,
                        null,
                        userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
