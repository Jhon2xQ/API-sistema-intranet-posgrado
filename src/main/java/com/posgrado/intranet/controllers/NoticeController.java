package com.posgrado.intranet.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posgrado.intranet.dtos.ApiResponse;
import com.posgrado.intranet.dtos.AvisoDtoRes;
import com.posgrado.intranet.dtos.EnlaceResDto;
import com.posgrado.intranet.services.NoticeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {
  
  private final NoticeService noticeService;

  @GetMapping("/avisos")
  public ResponseEntity<ApiResponse<List<AvisoDtoRes>>> getAllActiveAvisos() {
    try {
      List<AvisoDtoRes> avisos = noticeService.getAllActiveAvisos();
      return ResponseEntity.ok(new ApiResponse<>(true, "Avisos obtenidos correctamente", avisos));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(ApiResponse.error("error al obtener avisos: " + e.getMessage()));
    }
  }

  @GetMapping("/enlaces")
  public ResponseEntity<ApiResponse<List<EnlaceResDto>>> getAllActiveEnlaces() {
    try {
      List<EnlaceResDto> enlaces = noticeService.getAllActiveEnlaces();
      return ResponseEntity.ok(new ApiResponse<>(true, "Enlaces obtenidos correctamente", enlaces));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(ApiResponse.error("error al obtener enlaces: " + e.getMessage()));
    }
  }
}
