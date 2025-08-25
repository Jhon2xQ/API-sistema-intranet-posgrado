package com.posgrado.intranet.common.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.posgrado.intranet.entities.TbResidentadoUsuario;
import com.posgrado.intranet.repositories.ResidentadoUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final ResidentadoUsuarioRepository rUsuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    TbResidentadoUsuario rusuario = rUsuarioRepository.findById(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    
    return new CustomUserDetails(rusuario);
  }
}
