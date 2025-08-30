package com.posgrado.intranet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbCarrera;

@Repository
public interface CarreraRepository extends JpaRepository<TbCarrera, String>{

}
