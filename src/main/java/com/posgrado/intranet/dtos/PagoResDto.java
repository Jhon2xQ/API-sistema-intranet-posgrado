package com.posgrado.intranet.dtos;

import java.util.List;

import com.posgrado.intranet.entities.TbPagoDetalle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResDto {
  private List<TbPagoDetalle> pagos;
  private Double totalPrograma;
  private Double totalPagado;
  private Double totalPendiente;
}
