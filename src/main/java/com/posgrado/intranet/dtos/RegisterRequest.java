package com.posgrado.intranet.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
  @NotBlank(message = "Es necesario el usuario")
  private String usuario;
  @NotBlank(message = "Es necesario la contrasenia")
  private String contrasenia;
}
