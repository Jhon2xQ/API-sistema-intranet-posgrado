package com.posgrado.intranet.common.middlewares;

import java.io.IOException;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.posgrado.intranet.common.config.CustomUserDetails;
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
  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    try {
      boolean isRefreshEndpoint = request.getRequestURI().contains("/api/auth/refresh");
      if (!isRefreshEndpoint) {
        String jwt = jwtUtil.getAccessTokenFromRequest(request);
        if (jwt != null && jwtUtil.validateToken(jwt) && !jwtUtil.isRefreshToken(jwt)) {
          String username = jwtUtil.getUsernameFromToken(jwt);
          String carrera = jwtUtil.getCarreraFromToken(jwt);
          String especialidad = jwtUtil.getEspecialidadFromToken(jwt);
          List<String> roles = jwtUtil.getRolesFromToken(jwt);

          UserDetails userDetails = new CustomUserDetails(username, carrera, especialidad, roles);
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities()
          );
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    } catch (Exception e) {
      log.error("Error en autenticación JWT: {}", e.getMessage());
      SecurityContextHolder.clearContext();
    }
    filterChain.doFilter(request, response);
  }
}
