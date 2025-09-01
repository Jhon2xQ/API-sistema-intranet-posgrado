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

@Table(name = "tbResidentadoEnlaces", schema = "dbo")
public class TbResidentadoEnlace implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "titulo")
  private String titulo;

  @Column(name = "enlace")
  private String enlace;

  @Column(name = "estado")
  private Boolean estado;
}
