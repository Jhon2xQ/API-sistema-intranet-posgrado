package com.posgrado.intranet.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posgrado.intranet.common.config.CustomUserDetails;
import com.posgrado.intranet.dtos.AcademicoDto;
import com.posgrado.intranet.dtos.ApiResponse;
import com.posgrado.intranet.dtos.PersonalDto;
import com.posgrado.intranet.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  
  private final UserService userService;

  @GetMapping("/academica")
  public ResponseEntity<ApiResponse<AcademicoDto>> getInfoAcademica(
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    try {
      AcademicoDto academicoDto = userService.getInformacionAcademica(
      userDetails.getUsername(), userDetails.getCarrera(), userDetails.getEspecialidad()
      );
      return ResponseEntity.ok(ApiResponse.success("Información académica obtenida", academicoDto));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ApiResponse.error("Error al obtener la información académica: " + e.getMessage()));
    }
  }
  
  @GetMapping("/personal")
  public ResponseEntity<ApiResponse<PersonalDto>> getInfoPersonal(
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    try {
      PersonalDto personalDto = userService.getInformacionPersonal(
      userDetails.getUsername()
      );
      return ResponseEntity.ok(ApiResponse.success("Información personal obtenida", personalDto));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ApiResponse.error("Error al obtener la información personal: " + e.getMessage()));
    }
  }
}
