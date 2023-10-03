package alura.foro.api.domain.respuesta;

import java.time.LocalDateTime;
import java.util.List;

import alura.foro.api.domain.topico.Topico;
import alura.foro.api.domain.usuarios.Usuario;

public record DatosListaRespuesta(Long id, String mensaje, LocalDateTime fechaCreacion,
		String autor, Boolean solucion) {
	public DatosListaRespuesta(Respuesta respuesta) {
		this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaCreacion(),
				respuesta.getAutor().getLogin(), respuesta.getSolucion());
	}

	

}
