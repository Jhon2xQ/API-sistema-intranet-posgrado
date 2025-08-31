package com.posgrado.intranet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbHomologacion;
import com.posgrado.intranet.entities.TbNota;

@Repository
public interface HomologacionRepository extends JpaRepository<TbHomologacion, String> {
  
  @Query(value = """
      SELECT h.id, h.curso, h.semestre, h.carrera, h.grupo, h.especialidad, h.curricula, h.tipo_nota, h.alumno, h.estado, h.curso_h, h.curricula_h, h.especialidad_h, h.variante_h, h.grupo_h, h.resolucion, n.nota
      FROM Seguimiento.tbNota n
      JOIN Seguimiento.tbHomologacion h
          ON h.id_curso_original = n.id_curso_concat
      WHERE n.alumno = :alumno
        AND n.carrera = :carrera
        AND n.especialidad = :especialidad
        AND n.curricula >= :curricula
        AND n.estado = :estado;
      );
      """, nativeQuery = true
  )
  List<TbNota> findNotas(@Param("alumno") String alumno,
                          @Param("carrera") String carrera,
                          @Param("especialidad") String especialidad,
                          @Param("curricula") Integer curricula,
                          @Param("estado") String estado);
}
