package com.posgrado.intranet.dtos.auth;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
  @NotBlank(message = "Es necesario el usuario")
  private String usuario;
  @NotBlank(message = "es necesario la contraseña")
  private String contrasenia;
  private List<String> roles;
}
