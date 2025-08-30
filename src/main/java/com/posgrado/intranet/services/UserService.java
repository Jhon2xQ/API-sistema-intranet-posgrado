package com.posgrado.intranet.services;

import org.springframework.stereotype.Service;

import com.posgrado.intranet.dtos.user.AcademicoDto;
import com.posgrado.intranet.dtos.user.PersonalDto;
import com.posgrado.intranet.entities.TbAlumno;
import com.posgrado.intranet.entities.TbCarrera;
import com.posgrado.intranet.entities.TbEspecialidad;
import com.posgrado.intranet.entities.TbPersona;
import com.posgrado.intranet.repositories.AlumnoRepository;
import com.posgrado.intranet.repositories.CarreraRepository;
import com.posgrado.intranet.repositories.EspecialidadRepository;
import com.posgrado.intranet.repositories.PersonaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  
  private final AlumnoRepository alumnoRepository;
  private final PersonaRepository personaRepository;
  private final CarreraRepository carreraRepository;
  private final EspecialidadRepository especialidadRepository;  

  public AcademicoDto getInformacionAcademica(String alumnoId, String carreraId, String especialidadId) {
    TbPersona tbPersona = getPersona(alumnoId);
    TbCarrera tbCarrera = getCarrera(carreraId);
    TbEspecialidad tbEspecialidad = getEspecialidad(carreraId, especialidadId);

    AcademicoDto academicoDto = new AcademicoDto();
    academicoDto.setAlumno(alumnoId);
    academicoDto.setNombres(tbPersona.getNombres());
    academicoDto.setApellidoPaterno(tbPersona.getApellidoPaterno());
    academicoDto.setApellidoMaterno(tbPersona.getApellidoMaterno());
    academicoDto.setCarrera(tbCarrera.getNombre());
    academicoDto.setEspecialidad(tbEspecialidad.getDescripcion());

    return academicoDto;
  }

  public PersonalDto getInformacionPersonal(String alumnoId) {
    TbPersona tbPersona = getPersona(alumnoId);

    PersonalDto personalDto = new PersonalDto();
    personalDto.setAlumno(alumnoId);
    personalDto.setNombres(tbPersona.getNombres());
    personalDto.setApellidoPaterno(tbPersona.getApellidoPaterno());
    personalDto.setApellidoMaterno(tbPersona.getApellidoMaterno());
    personalDto.setNroDocumento(tbPersona.getNroDocumento());
    personalDto.setEmail(tbPersona.getEmail());
    personalDto.setDireccion(tbPersona.getDireccion());
    personalDto.setTelefono(tbPersona.getTelefono());

    return personalDto;
  }


  
  public TbPersona getPersona(String alumnoId) {
    TbAlumno alumno = alumnoRepository.findById(alumnoId).orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
    return personaRepository.findById(alumno.getPersona()).orElseThrow(() -> new RuntimeException("Persona no encontrada"));
  }

  public TbCarrera getCarrera(String carreraId) {
    return carreraRepository.findById(carreraId).orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
  }

  public TbEspecialidad getEspecialidad(String carreraId, String especialidadId) {
    return especialidadRepository.findByCarreraAndEspecialidad(carreraId, especialidadId)
        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
  }
}
