package com.posgrado.intranet.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posgrado.intranet.common.config.CustomUserDetails;
import com.posgrado.intranet.dtos.ApiResponse;
import com.posgrado.intranet.dtos.nota.NotaResDto;
import com.posgrado.intranet.services.NotaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estudiante/notas")
@RequiredArgsConstructor
public class NotaController {
  private final NotaService notaService;

  @GetMapping()
  public ResponseEntity<ApiResponse<List<NotaResDto>>> getNotas(@AuthenticationPrincipal CustomUserDetails userDetails) {
    try {
      List<NotaResDto> notas = notaService.getNotasRegular(
        userDetails.getUsername(), userDetails.getCarrera(), userDetails.getEspecialidad(), 0
      );
      return ResponseEntity.ok(ApiResponse.success("notas obtenidas con exito", notas));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(ApiResponse.error("error al obtener notas: " + e.getMessage()));
    }
  }
}
