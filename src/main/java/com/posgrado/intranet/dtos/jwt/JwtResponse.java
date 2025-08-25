package com.posgrado.intranet.dtos.jwt;

import lombok.Data;

@Data
public class JwtResponse {
  private String token;
    private String type = "Bearer";
    private String refreshToken;
    private String usuario;
    private Long expiresIn;
    
    public JwtResponse(String token, String refreshToken, String usuario, Long expiresIn) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.usuario = usuario;
        this.expiresIn = expiresIn;
    }
}
