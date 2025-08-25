package com.posgrado.intranet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.posgrado.intranet.entities.TbAlumnoCarrera;

public interface AlumnoCarreraRepository extends JpaRepository <TbAlumnoCarrera, String> {
  
}
