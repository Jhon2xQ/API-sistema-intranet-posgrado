package com.posgrado.intranet.dtos;

import java.util.List;

import lombok.Data;

@Data
public class NotaResDto {
  List<NotaDto> notas;
  Integer totalCreditos;
}
