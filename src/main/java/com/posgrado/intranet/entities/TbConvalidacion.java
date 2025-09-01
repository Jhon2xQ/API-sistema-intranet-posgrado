package com.posgrado.intranet.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
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

@Table(name = "tbConvalidacionReconocimiento", schema = "Seguimiento")
public class TbConvalidacion implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "alumno")
  private String alumno;

  @Column(name = "carrera")
  private String carrera;

  @Column(name = "especialidad")
  private String especialidad;

  @Column(name = "curso")
  private String curso;

  @Column(name = "estado")
  private String estado;

  @Column(name = "variante")
  private Integer variante;

  @Column(name = "curricula")
  private Integer curricula;

  @Column(name = "tipo_nota")
  private String tipoNota;

  @Column(name = "nota")
  private Integer nota;

  @Column(name = "resolucion")
  private String resolucion;

  @Column(name = "fecha_captura")
  private LocalDate fechaCaptura;
}
