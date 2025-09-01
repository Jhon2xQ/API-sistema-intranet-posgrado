package com.posgrado.intranet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbCurso;

@Repository
public interface CursoRepository extends JpaRepository<TbCurso, String> {
  Optional<TbCurso> findByCursoAndSemestreAndCarreraAndEspecialidadAndCurriculaAndGrupo(String curso, String semestre, String carrera, String especialidad, Integer curricula, String grupo);
}
