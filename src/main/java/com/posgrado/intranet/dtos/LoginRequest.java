package com.posgrado.intranet.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank(message = "usuario es obligatorio")
  private String usuario;
  @NotBlank(message = "la contraseña es obligatoria")
  private String contrasenia;
}
