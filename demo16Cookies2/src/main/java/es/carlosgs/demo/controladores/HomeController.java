package es.carlosgs.demo.controladores;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

  @GetMapping({"", "index"})
  public String welcome(HttpServletRequest request, HttpServletResponse response) {
    log.info("Página de bienvenida");

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String usuario = authentication.getName();

      // Comprobar si el navegador tenía cookie del usuario
      Optional<Cookie> cookieEncontrada = Arrays.stream(request.getCookies())
        .filter(cookie -> usuario.equals(cookie.getName()))
        .findAny();

      // si no existe la cookie el contador de visitas se pone a 1
      if (cookieEncontrada.isEmpty()) {  // si no existe la cookie >> primera visita
        Cookie cookie = new Cookie(usuario, "1");
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(7 * 24 * 60 * 60);  // 7 días
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

      } else {  // si existe la cookie se recupera el contador y se le suma 1
        Cookie cookie = cookieEncontrada.get();
        int contador = Integer.parseInt(cookie.getValue());
        cookie.setValue(String.valueOf(contador + 1));
        response.addCookie(cookie);
      }
    }

    return "index";
  }

  @GetMapping("/login")
  public String login(){
    return "login";
  }


  @GetMapping("/forbidden")
  public String forbidden(){
    return "forbidden";
  }

}
