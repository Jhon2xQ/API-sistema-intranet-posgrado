package com.posgrado.intranet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.posgrado.intranet.dtos.nota.NotaResDto;
import com.posgrado.intranet.entities.TbCurriculaCurso;
import com.posgrado.intranet.entities.TbCurso;
import com.posgrado.intranet.entities.TbNota;
import com.posgrado.intranet.repositories.CurriculaCursoRepository;
import com.posgrado.intranet.repositories.CursoRepository;
import com.posgrado.intranet.repositories.NotaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaService {
  private final NotaRepository notaRepository;
  private final CurriculaCursoRepository curriculaCursoRepository;
  private final CursoRepository cursoRepository;

  public String getNotasCompleto(String alumno, String carrera, String especialidad, Integer curricula) {
    return "Notas Service is working";
  }



  public List<NotaResDto> getNotasRegular(String alumno, String carrera, String especialidad, Integer curricula) {
    List<TbNota> notas = notaRepository.findNotas(alumno, carrera, especialidad, curricula, "A");
    return notas.stream().map(nota -> {
      TbCurriculaCurso ccurso = getCurriculaCurso(nota.getCurso(), 0, nota.getCarrera(), nota.getEspecialidad(), nota.getCurricula());
      TbCurso curso = getCurso(nota.getCurso(), nota.getSemestre(), nota.getCarrera(), nota.getEspecialidad(), nota.getCurricula(), nota.getGrupo());
      NotaResDto dto = new NotaResDto();
      dto.setCursoId(ccurso.getCurso());
      dto.setNombreCurso(ccurso.getNombre());
      dto.setCategoria(ccurso.getCategoria());
      dto.setCreditos(ccurso.getCreditos());
      dto.setNota(nota.getNota());
      dto.setFechaFin(curso.getFechaFinalizacion());
      dto.setSemestre(nota.getSemestre());
      dto.setTipoNota("R");
      return dto;
    }).toList();
  }

  public TbCurriculaCurso getCurriculaCurso(String curso, Integer variante, String carrera, String especialidad,
      Integer curricula) {
    return curriculaCursoRepository
        .findByCursoAndVarianteAndCarreraAndEspecialidadAndCurricula(curso, variante, carrera, especialidad, curricula)
        .orElseThrow(() -> new RuntimeException("No se encontró el curso con los parámetros proporcionados."));
  }
  
  public TbCurso getCurso(String curso, String semestre, String carrera, String especialidad, Integer curricula, String grupo) {
    return cursoRepository
        .findByCursoAndSemestreAndCarreraAndEspecialidadAndCurriculaAndGrupo(curso, semestre, carrera, especialidad, curricula, grupo)
        .orElseThrow(() -> new RuntimeException("No se encontró el curso con los parámetros proporcionados."));
  }
}
