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

@Table(name = "tbCurriculaCurso", schema = "Curricula")
public class TbCurriculaCurso implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "curso")
  private String curso;

  @Column(name = "variante")
  private Integer variante;

  @Column(name = "carrera")
  private String carrera;

  @Column(name = "especialidad")
  private String especialidad;

  @Column(name = "curricula")
  private Integer curricula;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "categoria")
  private String categoria;

  @Column(name = "creditos")
  private Integer creditos;
}
