package com.aira.backend.infrastructure.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            System.out.println("[JWT] Bearer token found: " + token);
        } else {
            System.out.println("[JWT] No valid Authorization header found");
        }

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.getEmailFromToken(token);
            System.out.println("[JWT] Token is valid. Extracted email: " + email);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            System.out.println("[JWT] Loaded user details: " + userDetails.getUsername());

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);

            System.out.println("[JWT] Security context set for user: " + email);
        } else if (token != null) {
            System.out.println("[JWT] Token is invalid or expired");
        }

        filterChain.doFilter(request, response);
    }
}

/**
 * JwtAuthenticationFilter is our quiet bouncer at the door
 * It checks every request for a valid token, gently unpacks it,
 * and if all is well, lets the user in with a smile (and full auth context) 😌🔐
 */
