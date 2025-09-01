package com.posgrado.intranet.services;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.posgrado.intranet.dtos.nota.NotaDto;
import com.posgrado.intranet.dtos.nota.NotaResDto;
import com.posgrado.intranet.entities.TbConvalidacion;
import com.posgrado.intranet.entities.TbCurricula;
import com.posgrado.intranet.entities.TbCurriculaCurso;
import com.posgrado.intranet.entities.TbCurso;
import com.posgrado.intranet.entities.TbHomologacion;
import com.posgrado.intranet.entities.TbNota;
import com.posgrado.intranet.repositories.ConvalidacionRepository;
import com.posgrado.intranet.repositories.CurriculaCursoRepository;
import com.posgrado.intranet.repositories.CurriculaRepository;
import com.posgrado.intranet.repositories.CursoRepository;
import com.posgrado.intranet.repositories.HomologacionRepository;
import com.posgrado.intranet.repositories.NotaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaService {
  private final NotaRepository notaRepository;
  private final HomologacionRepository homologacionRepository;
  private final ConvalidacionRepository convalidacionRepository;
  private final CurriculaCursoRepository curriculaCursoRepository;
  private final CurriculaRepository curriculaRepository;
  private final CursoRepository cursoRepository;

  public NotaResDto getNotasCompleto(String alumno, String carrera, String especialidad, Integer curricula) {
    List<NotaDto> notasRegular = getNotasRegular(alumno, carrera, especialidad, curricula);
    List<NotaDto> notasHomologacion = getNotasHomologacion(alumno, carrera, especialidad, curricula);
    List<NotaDto> notasConvalidacion = getNotasConvalidacion(alumno, carrera, especialidad, curricula);
    List<NotaDto> notasConpleto = Stream.of(notasRegular, notasHomologacion, notasConvalidacion).flatMap(List::stream).toList();
    Integer totalCreditos = getTotalCreditos(notasConpleto);
    NotaResDto resDto = new NotaResDto();
    resDto.setNotas(notasConpleto);
    resDto.setTotalCreditos(totalCreditos);
    return resDto;
  }

  public List<NotaDto> getNotasRegular(String alumno, String carrera, String especialidad, Integer curricula) {
    List<TbNota> notas = notaRepository.findNotas(alumno, carrera, especialidad, curricula, "A");
    return notas.stream().map(nota -> {
      TbCurriculaCurso curriculaCurso = getCurriculaCurso(nota.getCurso(), 0, nota.getCarrera(), nota.getEspecialidad(),
          nota.getCurricula());
      TbCurso curso = getCurso(nota.getCurso(), nota.getSemestre(), nota.getCarrera(), nota.getEspecialidad(),
          nota.getCurricula(), nota.getGrupo());
      TbCurricula curriculaData = getCurricula(nota.getCurricula(), nota.getCarrera());

      NotaDto dto = new NotaDto();
      dto.setCursoId(curriculaCurso.getCurso());
      dto.setNombreCurso(curriculaCurso.getNombre());
      dto.setCategoria(curriculaCurso.getCategoria());
      dto.setCreditos(curriculaCurso.getCreditos());
      dto.setNota(nota.getNota());
      dto.setNotaAprobacion(curriculaData.getNotaAprobacion());
      dto.setFechaFin(curso.getFechaFinalizacion());
      dto.setSemestre(nota.getSemestre());
      dto.setTipoNota("R");
      return dto;
    }).toList();
  }
  
  public List<NotaDto> getNotasHomologacion(String alumno, String carrera, String especialidad, Integer curricula) {
    List<TbHomologacion> notas = homologacionRepository.findNotas(alumno, carrera, especialidad, curricula, "A");
    return notas.stream().map(nota -> {
      TbCurriculaCurso curriculaCurso = getCurriculaCurso(nota.getCursoH(), nota.getVarianteH(), nota.getCarrera(),
          nota.getEspecialidadH(), nota.getCurriculaH());
      TbCurso curso = getCurso(nota.getCurso(), nota.getSemestre(), nota.getCarrera(), nota.getEspecialidad(),
          nota.getCurricula(), nota.getGrupo());
      TbCurricula curriculaData = getCurricula(nota.getCurriculaH(), nota.getCarrera());
      NotaDto dto = new NotaDto();
      dto.setCursoId(curriculaCurso.getCurso());
      dto.setNombreCurso(curriculaCurso.getNombre());
      dto.setCategoria(curriculaCurso.getCategoria());
      dto.setCreditos(curriculaCurso.getCreditos());
      dto.setNota(nota.getNota());
      dto.setFechaFin(curso.getFechaFinalizacion());
      dto.setSemestre(nota.getSemestre());
      dto.setTipoNota("H");
      dto.setNotaAprobacion(curriculaData.getNotaAprobacion());
      dto.setResolucion(nota.getResolucion());
      return dto;
    }).toList();
  }
  
  public List<NotaDto> getNotasConvalidacion(String alumno, String carrera, String especialidad, Integer curricula) {
    List<TbConvalidacion> notas = convalidacionRepository.findAllByAlumnoAndCarreraAndEspecialidadAndEstadoAndCurriculaGreaterThanEqual(alumno, carrera, especialidad, "A", curricula);
    return notas.stream().map(nota -> {
      TbCurriculaCurso curriculaCurso = getCurriculaCurso(nota.getCurso(), nota.getVariante(), nota.getCarrera(), nota.getEspecialidad(),
          nota.getCurricula());
      TbCurricula curriculaData = getCurricula(nota.getCurricula(), nota.getCarrera());
      NotaDto dto = new NotaDto();
      dto.setCursoId(nota.getCurso());
      dto.setNombreCurso(curriculaCurso.getNombre());
      dto.setCategoria(curriculaCurso.getCategoria());
      dto.setCreditos(curriculaCurso.getCreditos());
      dto.setNota(nota.getNota());
      dto.setFechaFin(nota.getFechaCaptura());
      dto.setSemestre(nota.getFechaCaptura().getYear()+"-"+1);
      dto.setTipoNota("C");
      dto.setNotaAprobacion(curriculaData.getNotaAprobacion());
      dto.setResolucion(nota.getResolucion());
      return dto;
    }).toList();
  }

  public TbCurriculaCurso getCurriculaCurso(String curso, Integer variante, String carrera, String especialidad,
      Integer curricula) {
    return curriculaCursoRepository
        .findByCursoAndVarianteAndCarreraAndEspecialidadAndCurricula(curso, variante, carrera, especialidad, curricula)
        .orElseThrow(() -> new RuntimeException("No se encontró el curso con los parámetros proporcionados."));
  }
  
  public TbCurso getCurso(String curso, String semestre, String carrera, String especialidad, Integer curricula,
      String grupo) {
    return cursoRepository
        .findByCursoAndSemestreAndCarreraAndEspecialidadAndCurriculaAndGrupo(curso, semestre, carrera, especialidad,
            curricula, grupo)
        .orElseThrow(() -> new RuntimeException("No se encontró el curso con los parámetros proporcionados."));
  }

  public TbCurricula getCurricula(Integer curricula, String carrera) {
    return curriculaRepository.findByCurriculaAndCarreraAndEstado(curricula, carrera, "A")
        .orElseThrow(() -> new RuntimeException("No se encontró la currícula con los parámetros proporcionados."));
  }
  
  public Integer getTotalCreditos(List<NotaDto> notas) {
    return notas.stream().filter(nota -> nota.getNota() >= nota.getNotaAprobacion()).mapToInt(NotaDto::getCreditos).sum();
  }
}
