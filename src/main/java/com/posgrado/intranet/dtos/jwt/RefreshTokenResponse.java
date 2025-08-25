package com.posgrado.intranet.dtos.jwt;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenResponse {
  @NotBlank(message = "El refresh token es obligatorio")
  private String refreshToken;
}
