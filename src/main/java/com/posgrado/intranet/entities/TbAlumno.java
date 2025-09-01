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

@Table(name = "tbAlumno", schema = "Academico")
public class TbAlumno implements Serializable{
  @Serial 
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "alumno")
  private String alumno;

  @Column(name = "persona")
  private Integer persona;
}
