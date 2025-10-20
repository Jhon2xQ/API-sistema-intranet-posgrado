package com.posgrado.intranet.common.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.posgrado.intranet.entities.TbResidentadoUsuario;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
  private final TbResidentadoUsuario usuario;
  private final String carrera;
  private final String especialidad;
  private final Integer curricula;
  private final String username;
  private final List<String> authorities;

  // Constructor para cuando se crea desde la BD (login)
  public CustomUserDetails(TbResidentadoUsuario usuario, String carrera, String especialidad, Integer curricula) {
    this.usuario = usuario;
    this.carrera = carrera;
    this.especialidad = especialidad;
    this.curricula = curricula;
    this.username = null;
    this.authorities = null;
  }

  // Constructor para cuando se crea desde el token JWT (filtro)
  public CustomUserDetails(String username, String carrera, String especialidad, Integer curricula, List<String> authorities) {
    this.usuario = null;
    this.carrera = carrera;
    this.especialidad = especialidad;
    this.curricula = curricula;
    this.username = username;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (authorities != null) {
      return authorities.stream()
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
    } else {
      return usuario.getRoles().stream()
          .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre()))
          .collect(Collectors.toSet());
    }
  }

  @Override
  public String getUsername() {
    if (username != null) {
      return username;
    } else {
      return usuario.getUsuario();
    }
  }

  @Override
  public String getPassword() {
    if (usuario != null) {
      return usuario.getContrasenia();
    }
    return ""; // No necesitamos contraseña para JWT
  }

  public boolean getPrimeraSesion() {
    if (usuario != null) {
      return usuario.getCreatedAt().equals(usuario.getUpdatedAt());
    }
    return false; // No necesitamos este dato para JWT
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    if (usuario != null) {
      return usuario.isEstado();
    }
    return true;
  }
}