package com.posgrado.intranet.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posgrado.intranet.common.config.CustomUserDetails;
import com.posgrado.intranet.dtos.ApiResponse;
import com.posgrado.intranet.dtos.PagoResDto;
import com.posgrado.intranet.services.PagoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {
  private final PagoService pagoService;

  @GetMapping()
  public ResponseEntity<ApiResponse<PagoResDto>> getPagosByAlumno(
    @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    try {
      PagoResDto pagosResDto = pagoService.getPagosCompletoByAlumno(userDetails.getUsername());
      return ResponseEntity.ok(ApiResponse.success("Pagos obtenidos exitosamente", pagosResDto));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(
          ApiResponse.error("Error al obtener los pagos: " + e.getMessage()));
    }
  }
}
