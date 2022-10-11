package es.carlosgs.demo.controladores;

import es.carlosgs.demo.entidades.Empleado;
import es.carlosgs.demo.servicios.EmpleadoService;
import es.carlosgs.demo.storage.StorageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;


@Controller
@Data
@Slf4j
@RequestMapping("/empleado")
public class EmpleadoController {

  private final EmpleadoService servicio;

  private final StorageService storageService;

  @GetMapping("/list")
  public String listado(Model model) {
    model.addAttribute("listaEmpleados", servicio.findAll());
    return "list";
  }

  @GetMapping("/new")
  public String nuevoEmpleadoForm(Model model) {
    model.addAttribute("empleadoForm", new Empleado());
    return "form";
  }

  @PostMapping("/new/submit")
  public String nuevoEmpleadoSubmit(@Valid @ModelAttribute("empleadoForm") Empleado nuevoEmpleado,
                                    BindingResult bindingResult, @RequestParam("file") MultipartFile file) {

    if (bindingResult.hasErrors()) {
      log.info("hay errores en el formulario");
      bindingResult.getFieldErrors()
        .forEach(e -> log.info("field: " + e.getField() + ", rejected value: " + e.getRejectedValue()));
      return "form";
    } else {
      if (!file.isEmpty()) {
        log.info("hay imagen");
        String avatar = storageService.store(file, nuevoEmpleado.getId());
        nuevoEmpleado.setImagen(MvcUriComponentsBuilder
          .fromMethodName(EmpleadoController.class, "serveFile", avatar).build().toUriString());
        log.info("imagen {}", nuevoEmpleado.getImagen());
      }
      servicio.add(nuevoEmpleado);
      return "redirect:/empleado/list";
    }

  }

  @GetMapping("/edit/{id}")
  public String editarEmpleadoForm(@PathVariable long id, Model model) {

    Empleado empleado = servicio.findById(id);
    if (empleado != null) {
      model.addAttribute("empleadoForm", empleado);
      return "form";
    } else {
      return "redirect:/empleado/new";
    }
  }

  @PostMapping("/edit/submit")
  public String editarEmpleadoSubmit(@Valid @ModelAttribute("empleadoForm") Empleado empleado,
                                     BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "form";
    } else {
      servicio.edit(empleado);
      return "redirect:/empleado/list";
    }
  }

  @GetMapping("/delete/{id}")
  public String borrarEmpleado(@PathVariable("id") Long id, Model model) {

    Empleado empleado = servicio.findById(id);

    if (empleado != null)
      servicio.delete(empleado);

    return "redirect:/empleado/list";
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
    Resource file = storageService.loadAsResource(filename);
    return ResponseEntity.ok().body(file);
  }


}
