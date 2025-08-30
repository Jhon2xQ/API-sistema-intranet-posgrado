package com.posgrado.intranet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbEspecialidad;

@Repository
public interface EspecialidadRepository extends JpaRepository<TbEspecialidad, String>{
  Optional<TbEspecialidad> findByCarreraAndEspecialidad(String carrera, String especialidad);
}
