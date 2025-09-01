package com.posgrado.intranet.dtos;

import lombok.Data;

@Data
public class EnlaceResDto {
  private String titulo;
  private String enlace;

  public EnlaceResDto(String titulo, String enlace) {
    this.titulo = titulo;
    this.enlace = enlace;
  }
}
