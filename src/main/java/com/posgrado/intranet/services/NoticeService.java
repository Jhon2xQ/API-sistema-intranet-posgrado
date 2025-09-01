package com.posgrado.intranet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.posgrado.intranet.dtos.AvisoDtoRes;
import com.posgrado.intranet.dtos.EnlaceResDto;
import com.posgrado.intranet.entities.TbResidentadoAviso;
import com.posgrado.intranet.entities.TbResidentadoEnlace;
import com.posgrado.intranet.repositories.ResidentadoAvisoRepository;
import com.posgrado.intranet.repositories.ResidentadoEnlaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
  
  private final ResidentadoAvisoRepository residentadoAvisoRepository;
  private final ResidentadoEnlaceRepository residentadoEnlaceRepository;

  public List<AvisoDtoRes> getAllActiveAvisos() {
    List<TbResidentadoAviso> avisos = residentadoAvisoRepository.findByEstadoTrue();
    return avisos.stream()
        .map(aviso -> new AvisoDtoRes(
            aviso.getTitulo(),
            aviso.getCuerpo(),
            aviso.getEnlaceImagen(),
            aviso.getEnlaceVerMas()))
        .toList();
  }

  public List<EnlaceResDto> getAllActiveEnlaces() {
    List<TbResidentadoEnlace> enlaces = residentadoEnlaceRepository.findByEstadoTrue();
    return enlaces.stream()
        .map(enlace -> new EnlaceResDto(
            enlace.getTitulo(),
            enlace.getEnlace()))
        .toList();
  }
}
