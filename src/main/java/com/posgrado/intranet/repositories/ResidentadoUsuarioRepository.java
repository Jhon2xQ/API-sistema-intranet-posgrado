package com.posgrado.intranet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbResidentadoUsuario;

@Repository
public interface ResidentadoUsuarioRepository extends JpaRepository<TbResidentadoUsuario, String>{
  boolean existsByUsuario(String usuario);
}
