package com.posgrado.intranet.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

@Table(name = "tbResidentadoPerfil", schema = "Seguridad")
public class TbResidentadoPerfil implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idPerfil")
  private int idperfil;

  @Column(name = "nombre")
  private String nombre;

  @ManyToMany(mappedBy = "roles")
  private Set<TbResidentadoUsuario> usuarios = new HashSet<>();
}
