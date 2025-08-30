package com.posgrado.intranet.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicoDto {
  private String alumno;
  private String nombres;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private String carrera;
  private String especialidad;
}
