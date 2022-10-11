package es.carlosgs.demo.entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Empleado {

  @Min(value=0, message="{empleado.id.mayorquecero}")
  private long id;
  @NotEmpty
  private String nombre;
  @Email
  private String email;
  private String telefono;
  private boolean directivo;
  private TipoDepartamento departamento;
  private String imagen;
}
