package com.posgrado.intranet.dtos.nota;

import java.util.List;

import lombok.Data;

@Data
public class NotaResDto {
  List<NotaDto> notas;
  Integer totalCreditos;
}
