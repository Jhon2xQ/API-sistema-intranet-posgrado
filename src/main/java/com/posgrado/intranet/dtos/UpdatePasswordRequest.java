package com.posgrado.intranet.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
  @NotBlank(message = "Es necesario la nueva contrasenia")
  private String nuevaContrasenia;
}
