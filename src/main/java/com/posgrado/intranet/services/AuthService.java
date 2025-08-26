package com.posgrado.intranet.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.posgrado.intranet.common.config.CustomUserDetails;
import com.posgrado.intranet.common.config.CustomUserDetailsService;
import com.posgrado.intranet.common.utils.CookieUtil;
import com.posgrado.intranet.common.utils.JwtUtil;
import com.posgrado.intranet.dtos.auth.LoginRequest;
import com.posgrado.intranet.dtos.jwt.JwtResponse;
import com.posgrado.intranet.dtos.jwt.RefreshTokenResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final CustomUserDetailsService userDetailsService;
  private final CookieUtil cookieUtil;
  private final JwtUtil jwtUtil;

  @Transactional
  public JwtResponse login(LoginRequest loginRequest, HttpServletResponse response) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsuario(),
              loginRequest.getContrasenia()
      ));
      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
      String jwtToken = jwtUtil.generarToken(authentication);
      String refreshToken = jwtUtil.generarRefreshToken(userDetails.getUsername());
      cookieUtil.createAccessTokenCookie(response, jwtToken);
      cookieUtil.createRefreshTokenCookie(response, refreshToken);
      return new JwtResponse(jwtToken, refreshToken, userDetails.getUsername(), 84400000L);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("Credenciales invalidad");
    }
  }
  
  public JwtResponse refreshToken(RefreshTokenResponse request) {
    String refreshToken = request.getRefreshToken();
    if (!jwtUtil.validarToken(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
      throw new BadCredentialsException("Refresh token invalido");
    }
    String username = jwtUtil.getUsernameFromToken(refreshToken);
    CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());
    String newJwt = jwtUtil.generarToken(authentication);
    return new JwtResponse(newJwt, refreshToken, userDetails.getUsername(), 84400000L);
  }
}
