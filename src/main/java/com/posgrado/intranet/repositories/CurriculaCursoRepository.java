package com.posgrado.intranet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbCurriculaCurso;

@Repository
public interface CurriculaCursoRepository extends JpaRepository<TbCurriculaCurso, String> {
  Optional<TbCurriculaCurso> findByCursoAndVarianteAndCarreraAndEspecialidadAndCurricula(String curso, Integer variante, String carrera, String especialidad, Integer curricula);
}
