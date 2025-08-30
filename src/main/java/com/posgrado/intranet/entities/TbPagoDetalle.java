package com.posgrado.intranet.entities;

import java.io.Serial;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Table(name = "tbPagoDetalle", schema = "Caja")
public class TbPagoDetalle {
  
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "recibo")
  private String recibo;

  @Column(name = "alumno")
  private String alumno;

  @Column(name = "semestre")
  private String semestre;

  @Column(name = "monto")
  private Double monto;

  @Column(name = "estado")
  private String estado;

  @Column(name = "fecha")
  private Date fecha;

  @Column(name = "lugarpago")
  private String lugarPago;
}
