package com.posgrado.intranet.common.config;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.posgrado.intranet.entities.TbResidentadoUsuario;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
  private final TbResidentadoUsuario usuario;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre()))
        .collect(Collectors.toSet());
  }

  @Override
  public String getUsername() {
    return usuario.getUsuario();
  }

  @Override
  public String getPassword() {
    return usuario.getContrasenia();
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
    return usuario.isEstado();
  }
}
