package com.posgrado.intranet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbCurricula;

@Repository
public interface CurriculaRepository extends JpaRepository<TbCurricula, String> {
  Optional<TbCurricula> findByCurriculaAndCarreraAndEstado(Integer curricula, String carrera, String estado);
}
