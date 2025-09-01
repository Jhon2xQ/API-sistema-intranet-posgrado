package com.posgrado.intranet.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tbResidentadoUsuario", schema = "Seguridad")
public class TbResidentadoUsuario implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "usuario")
  private String usuario;

  @Column(name = "contrasenia")
  private String contrasenia;

  @Column(name = "estado")
  private boolean estado;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
    this.estado = true;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = Instant.now();
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "tbResidentadoUsuarioPerfil",
    schema = "Seguridad",
    joinColumns = @JoinColumn(name = "usuario"),
    inverseJoinColumns = @JoinColumn(name = "idPerfil")
  )
  private Set<TbResidentadoPerfil> roles;
}
