package com.posgrado.intranet.entities;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class TbHomologacion implements Serializable{
  @Serial 
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "curso")
  private String curso;

  @Column(name = "semestre")
  private String semestre;

  @Column(name = "carrera")
  private String carrera;

  @Column(name = "grupo")
  private String grupo;

  @Column(name = "especialidad")
  private String especialidad;

  @Column(name = "curricula")
  private Integer curricula;

  @Column(name = "estado")
  private String estado;
  
  @Column(name = "curso_h")
  private String cursoH;
  
  @Column(name = "curricula_h")
  private Integer curriculaH;

  @Column(name = "especialidad_h")
  private String especialidadH;

  @Column(name = "variante_h")
  private Integer varianteH;

  @Column(name = "grupo_h")
  private String grupoH;

  @Column(name = "resolucion")
  private String resolucion;

  @Column(name = "nota")
  private Integer nota;
}
