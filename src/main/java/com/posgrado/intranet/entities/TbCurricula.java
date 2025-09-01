package com.posgrado.intranet.entities;

import java.io.Serial;
import java.io.Serializable;

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

@Table(name = "tbCurricula", schema = "Curricula")
public class TbCurricula implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "curricula")
  private Integer curricula;

  @Column(name = "carrera")
  private String carrera;

  @Column(name = "estado")
  private String estado;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "nota_aprobacion")
  private Integer notaAprobacion;

  @Column(name = "nota_desaprobacion")
  private Integer notaDesaprobacion;
}
