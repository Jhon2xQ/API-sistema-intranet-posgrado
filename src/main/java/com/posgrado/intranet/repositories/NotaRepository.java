package com.posgrado.intranet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbNota;

@Repository
public interface NotaRepository extends JpaRepository<TbNota, String> {
  
  @Query(value = """
      SELECT n.id, n.alumno, n.semestre, n.curso, n.carrera, n.grupo, n.curricula, n.especialidad, n.nota
      FROM [Academico_Maestria].[Seguimiento].[tbNota] n
      WHERE n.alumno = :alumno
        AND n.carrera = :carrera
        AND n.especialidad = :especialidad
        AND n.curricula >= :curricula
        AND n.estado = :estado
        AND NOT EXISTS (
            SELECT 1
            FROM [Academico_Maestria].[Seguimiento].[tbHomologacion] h
            WHERE h.alumno = n.alumno
              AND h.carrera = n.carrera
              AND h.especialidad = n.especialidad
              AND h.curso = n.curso
              AND h.semestre = n.semestre
              AND h.grupo = n.grupo
              AND h.curricula = n.curricula
              AND h.tipo_nota = n.tipo_nota
      );
      """, nativeQuery = true
  )
  List<TbNota> findNotas(@Param("alumno") String alumno,
                          @Param("carrera") String carrera,
                          @Param("especialidad") String especialidad,
                          @Param("curricula") Integer curricula,
                          @Param("estado") String estado);
}
