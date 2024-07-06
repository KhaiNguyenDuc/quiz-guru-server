package com.quizguru.auth.filter;

import com.quizguru.auth.jwt.JwtTokenProvider;
import com.quizguru.auth.model.UserPrincipal;
import com.quizguru.auth.service.impl.SecurityUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final SecurityUserService securityUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {

        try {
            String token = tokenProvider.resolveToken(request);
            if(StringUtils.hasText(token) && tokenProvider.validateToken(token)) {

                String userId = tokenProvider.getUserIdFromJwt(token);
                UserPrincipal userPrincipal = (UserPrincipal) securityUserService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userPrincipal,
                                null,
                                userPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch(Exception e) {
            throw e;
//            throw new UnauthorizedException(Constant.UNAUTHORIZED_MSG);
        }

        filterChain.doFilter(request, response);
    }
}
