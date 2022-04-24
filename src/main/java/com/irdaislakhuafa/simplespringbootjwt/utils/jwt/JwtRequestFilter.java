package com.irdaislakhuafa.simplespringbootjwt.utils.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.irdaislakhuafa.simplespringbootjwt.model.entities.User;
import com.irdaislakhuafa.simplespringbootjwt.services.UserService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtUtility jwtUtility;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        log.info("Get `Authorization` variable from header");
        final String authorization = request.getHeader(JwtFinalVariable.AUTHORIZATION);

        String token = null, userId = null;

        if (authorization != null && authorization.startsWith(JwtFinalVariable.BEARER)) {
            log.info("Success get `Authorization` from header");

            log.info("Getting token from header");
            token = authorization.substring(JwtFinalVariable.BEARER.length());
            log.info("Success get token");

            log.info("Getting user id from token");
            userId = jwtUtility.getParticularClaim(token, Claims::getSubject).trim();
            log.info("Success get user id");

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                log.info("Get user information from userId");
                Optional<User> user = userService.findById(userId);

                log.info("Validating token");
                if (jwtUtility.isTokenValid(token, user.get())) {
                    log.info("Token is valid");

                    log.info("Preparing user authentication");
                    UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(
                            user.get(),
                            user.get().getId(),
                            user.get().getAuthorities());

                    log.info("Set details user authentication");
                    authUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("Success build details");

                    log.info("Register principal to SecurityContextHolder");
                    SecurityContextHolder.getContext().setAuthentication(authUser);
                    log.info("Success register principal");

                } else {
                    log.info("Token is not valid");
                }
            }

        } else {
            log.info("Failed to get `Authorization` from header because empty");
        }

        log.info("Continue to next filter");
        filterChain.doFilter(request, response);
    }

}
