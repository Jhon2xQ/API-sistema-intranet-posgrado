package com.posgrado.intranet.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posgrado.intranet.common.config.CustomUserDetails;
import com.posgrado.intranet.dtos.ApiResponse;

@RestController
public class TestController {

  @GetMapping("/health")
  public ResponseEntity<Map<String, Object>> health() {
    Map<String, Object> health = new HashMap<>();
    health.put("status", "UP");
    health.put("timestamp", LocalDateTime.now());
    health.put("service", "Spring Security JWT API");
    return ResponseEntity.ok(health);
  }
  
  @GetMapping("/public")
  public ResponseEntity<ApiResponse<String>> publicEndpoint() {
    return ResponseEntity.ok(
        ApiResponse.success("Endpoint público - acceso sin autenticación",
            "Este endpoint no requiere token JWT"));
  }
  
  @GetMapping("/protected")
  public ResponseEntity<ApiResponse<String>> protectedEndpoint(
          @AuthenticationPrincipal CustomUserDetails userDetails) {
      
    String username = userDetails.getUsername();
      
    return ResponseEntity.ok(
        ApiResponse.success("Acceso autorizado", username)
    );
  }
}
