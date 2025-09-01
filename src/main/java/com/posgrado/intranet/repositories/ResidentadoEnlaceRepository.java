package com.posgrado.intranet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbResidentadoEnlace;

@Repository
public interface ResidentadoEnlaceRepository extends JpaRepository<TbResidentadoEnlace, Integer> {
  List<TbResidentadoEnlace> findByEstadoTrue();
}
