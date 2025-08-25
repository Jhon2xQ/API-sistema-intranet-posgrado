package com.posgrado.intranet.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "tbResidentadoUsuario", schema = "Seguridad")
public class TbResidentadoUsuario implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "usuario")
  private String usuario;

  @Column(name = "contraseña")
  private String contrasenia;

  @Column(name = "estado")
  private boolean estado;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "tbResidentadoUsuarioPerfil",
        schema = "Seguridad",
            joinColumns = @JoinColumn(name = "usuario"),
                inverseJoinColumns = @JoinColumn(name = "idPerfil")
  )
  private Set<TbResidentadoPerfil> roles = new HashSet<>();
}
