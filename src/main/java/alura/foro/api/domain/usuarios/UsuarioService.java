package alura.foro.api.domain.usuarios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import alura.foro.api.domain.publicacion.Publicacion;
import alura.foro.api.domain.respuesta.DatosListaRespuesta;
import alura.foro.api.domain.respuesta.DatosListaRespuestaConTopico;
import alura.foro.api.domain.respuesta.Respuesta;
import alura.foro.api.domain.topico.DatosListaTopico;
import alura.foro.api.domain.topico.Topico;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // El usuario autenticado se encuentra en authentication.getPrincipal()
            if (authentication.getPrincipal() instanceof Usuario) {
                return (Usuario) authentication.getPrincipal();
            }
        }
        
        return null;
    }
	
	public Page<Object> getHistorialUsuarioPaginado(String login, Pageable pageable) {
	    Usuario usuario = (Usuario) usuarioRepository.findByLogin(login);
	    List<Topico> topicos = usuario.getTopicos();
	    List<Respuesta> respuestas = usuario.getRespuestas();

	    // Crear una lista que contenga tanto Topicos como Respuestas (Publicacion es la superclase)
	    List<Publicacion> historialTotal = new ArrayList<>();
	    historialTotal.addAll(topicos);
	    historialTotal.addAll(respuestas);

	    // Ordenar la lista historialTotal por fechaCreacion en orden descendente
	    historialTotal.sort(Comparator.comparing(Publicacion::getFechaCreacion).reversed());

	    // Crear una lista para almacenar los DTO
	    List<Object> dtos = new ArrayList<>();

	    // Iterar sobre la lista historialTotal y crear los DTO correspondientes
	    for (Publicacion publicacion : historialTotal) {
	        if (publicacion instanceof Topico) {
	            // Es un Topico, crear y agregar un DTO DatosListaTopico
	            DatosListaTopico datosTopico = new DatosListaTopico((Topico) publicacion);
	            dtos.add(datosTopico);
	        } else if (publicacion instanceof Respuesta) {
	            // Es una Respuesta, crear y agregar un DTO DatosListaRespuestaConTopico
	            DatosListaRespuestaConTopico datosRespuesta = new DatosListaRespuestaConTopico((Respuesta) publicacion);
	            dtos.add(datosRespuesta);
	        }
	    }

	    // Realizar la paginación utilizando PageRequest
	    int pageSize = 10; // Tamaño de página deseado
	    int pageNumber = pageable.getPageNumber(); // Número de página solicitado

	    // Crear un objeto PageRequest para la consulta
	    PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

	    // Crear una lista que contiene los elementos de la página actual
	    int start = (int) pageRequest.getOffset();
	    int end = Math.min((start + pageRequest.getPageSize()), dtos.size());
	    List<Object> dtosPaginados = dtos.subList(start, end);

	    // Crear una instancia de Page<Object> con los resultados paginados
	    Page<Object> pagina = new PageImpl<>(dtosPaginados, pageRequest, dtos.size());

	    return pagina;
	}
	

	public Page<Usuario> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }
	
}	