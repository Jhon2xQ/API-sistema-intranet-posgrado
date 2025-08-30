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

@Table(name = "tbPersona", schema = "General")
public class TbPersona {

  @Serial 
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "persona")
  private Integer persona;

  @Column(name = "nro_documento")
  private String nroDocumento;
  
  @Column(name = "nombres")
  private String nombres;

  @Column(name = "apellido_paterno")
  private String apellidoPaterno;
  
  @Column(name = "apellido_materno")
  private String apellidoMaterno;

  @Column(name = "e_mail")
  private String email;

  @Column(name = "direccion")
  private String direccion;

  @Column(name = "telefono")
  private String telefono;
}
