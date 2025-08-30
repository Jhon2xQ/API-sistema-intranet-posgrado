package com.posgrado.intranet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbAlumno;

@Repository
public interface AlumnoRepository extends JpaRepository<TbAlumno, String> {
  
}
