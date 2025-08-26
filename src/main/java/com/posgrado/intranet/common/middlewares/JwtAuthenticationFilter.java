package com.posgrado.intranet.common.middlewares;

import java.io.IOException;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.posgrado.intranet.common.config.CustomUserDetailsService;
import com.posgrado.intranet.common.utils.CookieUtil;
import com.posgrado.intranet.common.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final CustomUserDetailsService userDetailsService;
  private final CookieUtil cookieUtil;
  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    try {
      // Primero intentar obtener token desde cookie
      Optional<String> tokenFromCookie = cookieUtil.getTokenFromCookie(request, cookieUtil.ACCESS_TOKEN_COOKIE);

      // Fallback a Authorization header si no hay cookie
      String jwt = tokenFromCookie.orElse(getJwtFromRequest(request));

      if (jwt != null && jwtUtil.validarToken(jwt) && !jwtUtil.isRefreshToken(jwt)) {
        String username = jwtUtil.getUsernameFromToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
            null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.debug("Usuario autenticado: {}", username);    
      }
    } catch (Exception e) {
      log.error("Error en autenticación JWT: {}", e.getMessage());
      SecurityContextHolder.clearContext();
    }
    filterChain.doFilter(request, response);
  }
  
  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
