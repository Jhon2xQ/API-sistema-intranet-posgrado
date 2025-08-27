package com.posgrado.intranet.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
  @NotBlank(message = "El refresh token es obligatorio")
  private String refreshToken;
}
