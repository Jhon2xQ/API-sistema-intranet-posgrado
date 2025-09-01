package com.posgrado.intranet.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDto {
  private String alumno;
  private String nombres;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private String nroDocumento;
  private String email;
  private String direccion;
  private String telefono;
}
