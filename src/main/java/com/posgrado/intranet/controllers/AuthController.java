package com.posgrado.intranet.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posgrado.intranet.common.config.CustomUserDetails;
import com.posgrado.intranet.dtos.ApiResponse;
import com.posgrado.intranet.dtos.JwtResponse;
import com.posgrado.intranet.dtos.LoginRequest;
import com.posgrado.intranet.dtos.RegisterRequest;
import com.posgrado.intranet.dtos.UpdatePasswordRequest;
import com.posgrado.intranet.entities.TbResidentadoUsuario;
import com.posgrado.intranet.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<JwtResponse>> login(
        @Valid @RequestBody LoginRequest loginRequest,
      HttpServletResponse response) {
    try {
      JwtResponse jwtResponse = authService.login(loginRequest, response);
      return ResponseEntity.ok(ApiResponse.success("Login exitoso", jwtResponse));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
          ApiResponse.error("Error de autenticacion"));
    }
  }
  
  @PostMapping("/register")
  public ResponseEntity<ApiResponse<TbResidentadoUsuario>> register(
      @Valid @RequestBody RegisterRequest registerRequest) {
    try {
      TbResidentadoUsuario usuario = authService.register(registerRequest);
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(ApiResponse.success("Usuario registrado exitosamente", usuario));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(ApiResponse.error("Error al registrar usuario: " + e.getMessage()));
    }
  }
  
  @PostMapping("/refresh")
  public ResponseEntity<ApiResponse<JwtResponse>> refreshToken(HttpServletRequest request) {
    try {
      JwtResponse jwtResponse = authService.refreshToken(request);
      return ResponseEntity.ok(
          ApiResponse.success("Token renovado exitosamente", jwtResponse));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(ApiResponse.error("Error al renovar token: " + e.getMessage()));
    }
  }

  @PutMapping("/password")
  public ResponseEntity<ApiResponse<String>> updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
      @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
    try {
      authService.updatePassword(userDetails.getUsername(), updatePasswordRequest.getNuevaContrasenia());
      return ResponseEntity.ok(ApiResponse.success("Contrasenia actualizada exitosamente"));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(ApiResponse.error("Error al actualizar la contraseña: " + e.getMessage()));
    }
  }
  
  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<String>> logout(HttpServletResponse response) {
    authService.logout(response);
    return ResponseEntity.ok(ApiResponse.success("Logout exitoso"));
  }
}