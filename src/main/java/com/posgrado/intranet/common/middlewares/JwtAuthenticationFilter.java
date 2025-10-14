package com.posgrado.intranet.common.middlewares;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posgrado.intranet.common.config.CustomUserDetails;
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
  private final ObjectMapper objectMapper;
  private final CookieUtil cookieUtil;
  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    try {
      boolean isRefreshEndpoint = request.getRequestURI().contains("/auth/refresh");
      boolean isRefreshMethod = request.getMethod().contains("POST");
      String accessToken = jwtUtil.getAccessTokenFromRequest(request);

      if (isRefreshEndpoint && isRefreshMethod) {
        handleRefreshRequest(request, response, filterChain, accessToken);
        return;
      }
      if (accessToken != null) {
        if (jwtUtil.validateToken(accessToken) && !jwtUtil.isRefreshToken(accessToken)) {
          String username = jwtUtil.getUsernameFromToken(accessToken);
          String carrera = jwtUtil.getCarreraFromToken(accessToken);
          String especialidad = jwtUtil.getEspecialidadFromToken(accessToken);
          Integer curricula = jwtUtil.getCurriculaFromToken(accessToken);
          List<String> roles = jwtUtil.getRolesFromToken(accessToken);

          UserDetails userDetails = new CustomUserDetails(username, carrera, especialidad, curricula, roles);
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if(jwtUtil.validateExpiredToken(accessToken) && !jwtUtil.isRefreshToken(accessToken)) {
          log.debug("Access token expirado");
          sendUnauthorizedResponse(response, "ACCESS_TOKEN_EXPIRED", 
              "Access token expirado");
          return;
        } else {
          log.debug("Access token inválido");
          sendUnauthorizedResponse(response, "INVALID_TOKEN", "Access token inválido");
          return;
        }
      }
    } catch (Exception e) {
      log.error("Error en autenticación JWT: {}", e.getMessage());
      SecurityContextHolder.clearContext();
      sendUnauthorizedResponse(response, "AUTHENTICATION_ERROR", "Error en autenticación");
      return;
    }
    filterChain.doFilter(request, response);
  }
  
  private void handleRefreshRequest(HttpServletRequest request, HttpServletResponse response, 
                                    FilterChain filterChain, String expiredAccessToken) 
      throws ServletException, IOException {
    if (expiredAccessToken == null || !jwtUtil.validateExpiredToken(expiredAccessToken)) {
      log.warn("Intento de refresh sin access token valido en header");
      sendUnauthorizedResponse(response, "MISSING_EXPIRED_TOKEN",
          "Access token valido expirado requerido en Authorization header para refresh");
      return;
    }
    if (!cookieUtil.hasRefreshToken(request) || !jwtUtil.isRefreshToken(cookieUtil.getRefreshTokenFromCookie(request))) {
      log.warn("Intento de refresh sin refresh token valido en cookie");
      sendUnauthorizedResponse(response, "MISSING_REFRESH_TOKEN",
          "Refresh token no encontrado en cookie");
      return;
    }
    filterChain.doFilter(request, response);
  }
  
  private void sendUnauthorizedResponse(HttpServletResponse response, String errorCode, String message) 
        throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("success", false);
    errorResponse.put("errorCode", errorCode);
    errorResponse.put("message", message);
    errorResponse.put("timestamp", System.currentTimeMillis());
    
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
