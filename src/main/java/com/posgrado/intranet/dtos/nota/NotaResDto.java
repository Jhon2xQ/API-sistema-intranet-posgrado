package com.posgrado.intranet.dtos.nota;

import java.util.Date;

import lombok.Data;

@Data
public class NotaResDto {
  private String cursoId;
  private String nombreCurso;
  private String categoria;
  private Integer creditos;
  private Integer nota;
  private String tipoNota;
  private String semestre;
  private String resolucion;
  private Date fechaFin;
}
