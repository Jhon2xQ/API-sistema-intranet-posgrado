package com.posgrado.intranet.dtos;

import lombok.Data;

@Data
public class JwtResponse {
  private String accessToken;
  private String usuario;
  private boolean primeraSesion;

  public JwtResponse(String accessToken, String usuario, boolean primeraSesion) {
      this.accessToken = accessToken;
      this.usuario = usuario;
      this.primeraSesion = primeraSesion;
  }
}
