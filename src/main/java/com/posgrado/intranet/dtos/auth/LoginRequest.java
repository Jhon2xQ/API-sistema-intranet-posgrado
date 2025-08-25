package com.posgrado.intranet.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank(message = "usuario es obligatorio")
  private String usuario;
  @NotBlank(message = "la contraseña es obligatoria")
  private String contrasenia;
}
