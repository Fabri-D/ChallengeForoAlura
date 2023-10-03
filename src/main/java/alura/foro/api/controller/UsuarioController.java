package alura.foro.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alura.foro.api.domain.usuarios.DatosListaUsuario;
import alura.foro.api.domain.usuarios.Usuario;
import alura.foro.api.domain.usuarios.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public ResponseEntity<Page<DatosListaUsuario>> listarUsuarios(@PageableDefault(size = 10) Pageable pageable) {
        // Obtener una lista paginada de usuarios
        Page<Usuario> usuarios = usuarioService.listarUsuarios(pageable);

        // Mapear la página de usuarios a DTO DatosUsuario
        Page<DatosListaUsuario> pageDatosUsuario = usuarios.map(DatosListaUsuario::new);

        return ResponseEntity.ok(pageDatosUsuario);
    }
    
    @GetMapping("/{login}")
    public ResponseEntity<Page<Object>> getHistorialUsuarioPaginado(@PathVariable String login, @PageableDefault(size = 10) Pageable pageable) {
        // Verificar si el usuario autenticado coincide con el login proporcionado
        UserDetails userDetails = usuarioService.getUsuarioAutenticado();
        if (userDetails == null || !userDetails.getUsername().equals(login)) {
            throw new AccessDeniedException("Solo puedes acceder a tu propio historial. Usuario autenticado: " + userDetails.getUsername());
        }

        // Obtener el historial del usuario por su login con paginación
        Page<Object> historial = usuarioService.getHistorialUsuarioPaginado(login, pageable);

        // Devolver el historial paginado en el cuerpo de la respuesta HTTP
        return ResponseEntity.ok(historial);
    }
    

}
