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

@Table(name = "tbResidentadoAvisos", schema = "dbo")
public class TbResidentadoAviso implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "titulo")
  private String titulo;

  @Column(name = "cuerpo")
  private String cuerpo;

  @Column(name = "enlace_imagen")
  private String enlaceImagen;

  @Column(name = "enlace_ver_mas")
  private String enlaceVerMas;

  @Column(name = "estado")
  private Boolean estado;
}
