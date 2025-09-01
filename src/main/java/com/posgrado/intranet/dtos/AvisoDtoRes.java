package com.posgrado.intranet.dtos;

import lombok.Data;

@Data
public class AvisoDtoRes {
  private String titulo;
  private String cuerpo;
  private String enlaceImagen;
  private String enlaceVerMas;

  public AvisoDtoRes(String titulo, String cuerpo, String enlaceImagen, String enlaceVerMas) {
    this.titulo = titulo;
    this.cuerpo = cuerpo;
    this.enlaceImagen = enlaceImagen;
    this.enlaceVerMas = enlaceVerMas;
  }
}
