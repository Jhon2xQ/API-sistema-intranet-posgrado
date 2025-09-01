package com.posgrado.intranet.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.posgrado.intranet.dtos.PagoResDto;
import com.posgrado.intranet.entities.TbPagoDetalle;
import com.posgrado.intranet.repositories.AlumnoCarreraRepository;
import com.posgrado.intranet.repositories.PagoDetalleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

  private final PagoDetalleRepository pagoDetalleRepository;
  private final AlumnoCarreraRepository alumnoCarreraRepository;

  public PagoResDto getPagosCompletoByAlumno(String alumno) {
    List<TbPagoDetalle> pagos = getPagosByAlumno(alumno);
    Double totalPrograma = getTotalProgramaByAlumno(alumno);
    Double totalPagado = getTotalPagadoByAlumno(pagos);
    Double totalPendiente = totalPrograma - totalPagado;

    return new PagoResDto(pagos, totalPrograma, totalPagado, totalPendiente);
  }

  public List<TbPagoDetalle> getPagosByAlumno(String alumno) {
    List<Object[]> results = pagoDetalleRepository.findPagosAlumno(alumno);
    return results.stream().map(result -> {
      TbPagoDetalle pagoDetalle = new TbPagoDetalle();
      pagoDetalle.setRecibo((String) result[0]);
      pagoDetalle.setAlumno((String) result[1]);
      pagoDetalle.setSemestre((String) result[2]);
      pagoDetalle.setMonto(((BigDecimal) result[3]).doubleValue());
      pagoDetalle.setEstado(result[4].toString());
      pagoDetalle.setFecha((Date) result[5]);
      pagoDetalle.setLugarPago((String) result[6]);
      return pagoDetalle;
    }).toList();
  }

  public Double getTotalPagadoByAlumno(List<TbPagoDetalle> pagos) {
    return pagos.stream()
        .mapToDouble(TbPagoDetalle::getMonto)
        .sum();
  }
  
  public Double getTotalProgramaByAlumno(String alumno) {
    return alumnoCarreraRepository.findByAlumnoAndEstadoAlumnoNot(alumno, 5).orElseThrow(() -> 
      new RuntimeException("No se encontró la carrera activa para el alumno: " + alumno)
      ).getMontoMaestria();
  }
}
