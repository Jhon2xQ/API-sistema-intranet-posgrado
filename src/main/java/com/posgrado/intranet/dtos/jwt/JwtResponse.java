package com.posgrado.intranet.dtos.jwt;

import lombok.Data;

@Data
public class JwtResponse {
  private String accessToken;
  private String usuario;
  
  public JwtResponse(String accessToken, String usuario) {
      this.accessToken = accessToken;
      this.usuario = usuario;
  }
}
