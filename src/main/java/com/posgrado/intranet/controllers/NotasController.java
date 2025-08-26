package com.posgrado.intranet.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posgrado.intranet.entities.TbAlumnoCarrera;
import com.posgrado.intranet.services.AlumnoCarreraService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("notas")
@RequiredArgsConstructor
public class NotasController {
  private final AlumnoCarreraService alumnoCarreraService;

  @GetMapping("{codigo}")
  public TbAlumnoCarrera getByCodigo(@PathVariable("codigo") String codigo) {
    return alumnoCarreraService.getByCodigo(codigo);
  }
}
