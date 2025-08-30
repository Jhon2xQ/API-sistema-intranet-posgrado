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
  private final String username;
  private final List<String> authorities;

  // Constructor para cuando se crea desde la BD (login)
  public CustomUserDetails(TbResidentadoUsuario usuario, String carrera, String especialidad) {
    this.usuario = usuario;
    this.carrera = carrera;
    this.especialidad = especialidad;
    this.username = null;
    this.authorities = null;
  }

  // Constructor para cuando se crea desde el token JWT (filtro)
  public CustomUserDetails(String username, String carrera, String especialidad, List<String> authorities) {
    this.usuario = null;
    this.carrera = carrera;
    this.especialidad = especialidad;
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