package com.posgrado.intranet.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.posgrado.intranet.common.config.CustomUserDetails;
import com.posgrado.intranet.common.config.CustomUserDetailsService;
import com.posgrado.intranet.common.utils.CookieUtil;
import com.posgrado.intranet.common.utils.JwtUtil;
import com.posgrado.intranet.dtos.auth.LoginRequest;
import com.posgrado.intranet.dtos.auth.RegisterRequest;
import com.posgrado.intranet.dtos.jwt.JwtResponse;
import com.posgrado.intranet.entities.TbResidentadoUsuario;
import com.posgrado.intranet.repositories.ResidentadoUsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final ResidentadoUsuarioRepository usuarioRepository;
  private final AuthenticationManager authenticationManager;
  private final CustomUserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final CookieUtil cookieUtil;
  private final JwtUtil jwtUtil;

  @Transactional
  public JwtResponse login(LoginRequest loginRequest, HttpServletResponse response) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsuario(),
              loginRequest.getContrasenia())
      );
      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
      String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
      String jti = jwtUtil.getJtiFromToken(refreshToken);
      String accessToken = jwtUtil.generateAccessToken(authentication, jti);
      cookieUtil.createRefreshTokenCookie(response, refreshToken);
      return new JwtResponse(accessToken, userDetails.getUsername());
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("Credenciales invalidas");
    }
  }
  
  @Transactional
  public TbResidentadoUsuario register(RegisterRequest registerRequest) {
    if (usuarioRepository.existsByUsuario(registerRequest.getUsuario())) {
        throw new RuntimeException("El usuario ya existe");
    }      
    TbResidentadoUsuario usuario = TbResidentadoUsuario.builder()
        .usuario(registerRequest.getUsuario())
        .contrasenia(passwordEncoder.encode(registerRequest.getContrasenia()))
        .build();
    return usuarioRepository.save(usuario);
  }
  
  @Transactional
  public JwtResponse refreshToken(HttpServletRequest request) {
    String accessToken = jwtUtil.getAccessTokenFromRequest(request);
    String refreshToken = cookieUtil.getRefreshTokenFromCookie(request);
    if (accessToken == null || refreshToken == null) {
      throw new BadCredentialsException("Tokens no proporcionados");
    }
    if (!jwtUtil.validateToken(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
      throw new BadCredentialsException("Refresh token invalido o expirado");
    }
    if (!jwtUtil.validateExpiredToken(accessToken)) {
      throw new BadCredentialsException("Access token invalido");
    }
    String accessTokenJti = jwtUtil.getJtiFromToken(accessToken);
    String refreshTokenJti = jwtUtil.getJtiFromToken(refreshToken);
    if (!accessTokenJti.equals(refreshTokenJti)) {
      throw new BadCredentialsException("Tokens no asociados");
    }
    String username = jwtUtil.getUsernameFromToken(refreshToken);
    CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities());
    String newJwt = jwtUtil.generateAccessToken(authentication, refreshTokenJti);
    return new JwtResponse(newJwt, userDetails.getUsername());
  }
  
  public void logout(HttpServletResponse response) {
    cookieUtil.clearTokenCookies(response);
  }
}
