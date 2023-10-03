package alura.foro.api.domain.respuesta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alura.foro.api.domain.topico.Topico;
import alura.foro.api.domain.topico.TopicoRepository;
import alura.foro.api.domain.usuarios.UsuarioService;
import alura.foro.api.infra.errores.ValidacionDeIntegridad;
import jakarta.validation.Valid;

@Service
public class RespuestaService {
	
	@Autowired
	RespuestaRepository respuestaRepository;
	
	@Autowired
	TopicoRepository topicoRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	public List<Respuesta> obtenerRespuestasPorIdTopico(Long idTopico) {
        return respuestaRepository.findByTopicoId(idTopico);
    }
	
	public void agendarRespuesta(DatosAgendarRespuesta datos, Long idTopico, Respuesta respuesta) {
        respuesta.setMensaje(datos.mensaje());
        var topico = (Topico) topicoRepository.getReferenceById(idTopico);
        respuesta.setTopico(topico);
        var autor = usuarioService.getUsuarioAutenticado();
        respuesta.setAutor(autor);

	}

	public Respuesta actualizarInformaciones(@Valid DatosActualizacionRespuesta datos, Respuesta respuesta) {
		if (datos.mensaje() != null) {
			respuesta.setMensaje(datos.mensaje());
		}

		if (datos.topico() != null) {
			try {
				Long Idtopico = datos.topico();
				Topico topico = topicoRepository.findById(Idtopico).orElse(null);

				if (topico != null) {
					respuesta.setTopico(topico);
				} else {
					throw new ValidacionDeIntegridad("El id del topico no fue encontrado");
				}
			} catch (NumberFormatException e) {
				// Maneja el caso en el que `datos.curso()` no sea un número válido.
			}
		}

		if (datos.fechaCreacion() != null) {
			respuesta.setFechaCreacion(datos.fechaCreacion());
		}

		if (datos.solucion() != null) {
            respuesta.setSolucion(datos.solucion());
        }
		return respuesta;
	}
	
}
