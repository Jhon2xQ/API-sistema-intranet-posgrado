package com.posgrado.intranet.dtos.nota;

import java.time.LocalDate;

import lombok.Data;

@Data
public class NotaDto {
  private String cursoId;
  private String nombreCurso;
  private String categoria;
  private Integer creditos;
  private Integer notaAprobacion;
  private Integer nota;
  private String tipoNota;
  private String semestre;
  private String resolucion;
  private LocalDate fechaFin;
}
