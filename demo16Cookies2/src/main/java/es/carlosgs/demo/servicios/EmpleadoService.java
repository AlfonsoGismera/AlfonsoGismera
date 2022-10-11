package es.carlosgs.demo.servicios;

import es.carlosgs.demo.entidades.Empleado;
import es.carlosgs.demo.entidades.TipoDepartamento;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class EmpleadoService {

  private List<Empleado> repositorio = new ArrayList<>();

  public Empleado add(Empleado e) {
    repositorio.add(e);
    return e;
  }

  public List<Empleado> findAll() {
    return repositorio;
  }

  public Empleado findById(long id) {
    Empleado result = null;
    boolean encontrado = false;
    int i = 0;
    while (!encontrado && i < repositorio.size()) {
      if (repositorio.get(i).getId() == id) {
        encontrado = true;
        result = repositorio.get(i);
      } else {
        i++;
      }
    }
    return result;
  }

  public Empleado edit(Empleado e) {
    boolean encontrado = false;
    int i = 0;
    while (!encontrado && i < repositorio.size()) {
      if (repositorio.get(i).getId() == e.getId()) {
        encontrado = true;
        repositorio.remove(i);
        repositorio.add(i, e);
      } else {
        i++;
      }
    }
    if (!encontrado)
      repositorio.add(e);

    return e;
  }

  public void delete(Empleado e) {
    repositorio.remove(e);
  }

  //  @PostConstruct
//  public void init() {
//    repositorio.addAll(
//      Arrays.asList(new Empleado(1,"Antonio García", "antonio.garcia@openwebinars.net", "954000000", true, TipoDepartamento.INFORMATICA),
//        new Empleado(2,"María López", "maria.lopez@openwebinars.net", "954000000", true, TipoDepartamento.VENTAS),
//        new Empleado(3,"Ángel Antúnez", "angel.antunez@openwebinars.net", "954000000", false, TipoDepartamento.RRHH)
//      )
//    );
//  }

  @PostConstruct
  public void init2() {
    repositorio.addAll(
      Arrays.asList(
        Empleado.builder()
          .id(1)
          .nombre("Antonio García")
          .email("antonio.garcia@openwebinars.net")
          .telefono("123456789")
          .directivo(true)
          .departamento(TipoDepartamento.INFORMATICA)
          .build(),
        Empleado.builder()
          .id(2)
          .nombre("María López")
          .email("maria.lopez@openwebinars.net")
          .telefono("954000000")
          .directivo(false)
          .departamento(TipoDepartamento.RRHH)
          .build(),
        Empleado.builder()
          .id(3)
          .nombre("Ángel Antúnez")
          .email("angel.antunez@openwebinars.net")
          .telefono("954000000")
          .directivo(false)
          .departamento(TipoDepartamento.VENTAS)
          .build()
      )
    );
  }

}
