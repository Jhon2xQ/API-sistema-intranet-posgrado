package com.posgrado.intranet.common.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.posgrado.intranet.entities.TbAlumnoCarrera;
import com.posgrado.intranet.entities.TbResidentadoUsuario;
import com.posgrado.intranet.repositories.AlumnoCarreraRepository;
import com.posgrado.intranet.repositories.ResidentadoUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final ResidentadoUsuarioRepository rUsuarioRepository;
  private final AlumnoCarreraRepository aCarreraRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    TbResidentadoUsuario usuario = rUsuarioRepository.findById(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    TbAlumnoCarrera alumno = aCarreraRepository.findByAlumnoAndEstadoAlumnoNot(username, 5)
        .orElseThrow(() -> new UsernameNotFoundException("Alumno desactivado"));
    return new CustomUserDetails(usuario, alumno.getCarrera(), alumno.getEspecialidad(), alumno.getCurricula());
  }
}
