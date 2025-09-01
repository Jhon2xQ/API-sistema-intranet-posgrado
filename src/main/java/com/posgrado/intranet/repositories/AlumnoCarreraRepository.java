package com.posgrado.intranet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbAlumnoCarrera;

@Repository
public interface AlumnoCarreraRepository extends JpaRepository <TbAlumnoCarrera, String> {
  Optional<TbAlumnoCarrera> findByAlumnoAndEstadoAlumnoNot(String alumno, int estado);
}
