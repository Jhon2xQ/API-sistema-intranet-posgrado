package com.posgrado.intranet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.posgrado.intranet.entities.TbPagoDetalle;

@Repository
public interface PagoDetalleRepository extends JpaRepository<TbPagoDetalle, String> {
  @Query(
        value = """
        SELECT recibo, alumno, semestre, monto, estado, fecha, lugarpago
        FROM [Academico_Maestria].[Caja].[tbPagoDetalle]
        WHERE alumno = :alumno
        ORDER BY semestre
        """, 
        nativeQuery = true
  )
  List<Object[]> findPagosAlumno(@Param("alumno") String alumno);
}
