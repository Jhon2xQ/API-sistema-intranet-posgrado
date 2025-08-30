package com.posgrado.intranet.entities;

import java.io.Serial;

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

@Table(name = "tbCarrera", schema = "Academico")
public class TbCarrera {

  @Serial 
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "carrera")
  private String carrera;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "tipo_carrera")
  private String tipoCarrera;
}
