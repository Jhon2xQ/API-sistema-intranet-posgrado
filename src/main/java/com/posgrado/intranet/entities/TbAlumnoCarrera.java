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
@Table(name = "tbAlumnoCarrera", schema = "Academico")
public class TbAlumnoCarrera implements Serializable {
  @Serial 
  private static final long serialVersionUID = 1L;

  /*
  esta entidad tiene como primary key (alumno, carrera, especialidad),
  en este caso no se usara filtros por ID ni nada por lo que lo ponemos
  el @Id a alumno, Spring necesita que se agregue si o si un Id.
  */

  @Id
  @Column(name = "alumno")
  private String alumno;

  @Column(name = "carrera")
  private String carrera;

  @Column(name = "especialidad")
  private String especialidad;

  @Column(name = "curricula")
  private int curricula;

  @Column(name = "estado_alumno")
  private int estadoAlumno;

  @Column(name = "monto_maestria")
  private float montoMaestria;

}
