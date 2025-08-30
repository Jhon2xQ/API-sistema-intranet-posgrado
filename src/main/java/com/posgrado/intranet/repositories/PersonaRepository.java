package com.posgrado.intranet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbPersona;

@Repository
public interface PersonaRepository extends JpaRepository<TbPersona, Integer>{
  
}
