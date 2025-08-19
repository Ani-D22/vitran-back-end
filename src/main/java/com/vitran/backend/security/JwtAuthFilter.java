package com.vitran.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenService tokenService;
    private final Set<String> publicPaths;
    private final AntPathMatcher matcher = new AntPathMatcher();

    public JwtAuthFilter(JwtTokenService tokenService, Set<String> publicPaths) {
        this.tokenService = tokenService;
        this.publicPaths = publicPaths == null ? Set.of() : publicPaths;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return publicPaths.stream().anyMatch(p -> matcher.match(p, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String auth = req.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = auth.substring(7);
        try {
            var jws = tokenService.parseAndValidate(token);
            var claims = jws.getBody();

            String subject = claims.getSubject(); // userLoginId
            if (subject == null) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            @SuppressWarnings("unchecked")
            var perms = (List<String>) claims.get("perms");
            var authorities = perms == null
                    ? List.<org.springframework.security.core.authority.SimpleGrantedAuthority>of()
                    : perms.stream()
                    .map(p -> new org.springframework.security.core.authority.SimpleGrantedAuthority("PERM_" + p))
                    .toList();

            var authentication = new org.springframework.security.authentication.AbstractAuthenticationToken(authorities) {
                @Override public Object getCredentials() { return token; }
                @Override public Object getPrincipal() { return subject; }
            };
            authentication.setAuthenticated(true);

            org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
