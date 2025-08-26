package com.posgrado.intranet.services;

import org.springframework.stereotype.Service;

import com.posgrado.intranet.entities.TbAlumnoCarrera;
import com.posgrado.intranet.repositories.AlumnoCarreraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlumnoCarreraService {
  private final AlumnoCarreraRepository alumnoCarreraRepo;

  public TbAlumnoCarrera getByCodigo(String codigo) {
    return alumnoCarreraRepo.findById(codigo).orElseThrow(() -> new RuntimeException("Alumno desactivado"));
  }
}
