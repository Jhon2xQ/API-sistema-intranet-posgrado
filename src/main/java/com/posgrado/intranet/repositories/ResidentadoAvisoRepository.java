package com.posgrado.intranet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbResidentadoAviso;

@Repository
public interface ResidentadoAvisoRepository extends JpaRepository<TbResidentadoAviso, Integer> {
  List<TbResidentadoAviso> findByEstadoTrue();
}
