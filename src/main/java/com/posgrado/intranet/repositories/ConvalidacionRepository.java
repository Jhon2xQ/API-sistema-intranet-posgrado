package com.posgrado.intranet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbConvalidacion;

@Repository
public interface ConvalidacionRepository extends JpaRepository<TbConvalidacion, String> {
  List<TbConvalidacion> findAllByAlumnoAndCarreraAndEspecialidadAndEstadoAndCurriculaGreaterThanEqual(String alumno, String carrera, String especialidad, String estado, Integer curricula);
}
