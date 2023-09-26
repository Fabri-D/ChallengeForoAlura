package alura.foro.api.domain.usuarios;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	
	public Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // El usuario autenticado se encuentra en authentication.getPrincipal()
            if (authentication.getPrincipal() instanceof Usuario) {
                return (Usuario) authentication.getPrincipal();
            }
        }

        // Si no se encuentra un usuario autenticado, puedes manejarlo como desees,
        // por ejemplo, lanzando una excepción o retornando null.
        return null;
    }
	
	
}
