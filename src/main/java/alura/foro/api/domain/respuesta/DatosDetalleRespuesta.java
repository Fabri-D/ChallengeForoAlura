package alura.foro.api.domain.respuesta;

import java.time.LocalDateTime;

import alura.foro.api.domain.topico.Topico;
import alura.foro.api.domain.usuarios.Usuario;

public record DatosDetalleRespuesta(Long id, String mensaje, Long idTopico, LocalDateTime fechaCreacion,
		String autor, Boolean solucion) {
	public DatosDetalleRespuesta(Respuesta respuesta) {
		this(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getId(), respuesta.getFechaCreacion(),
				respuesta.getAutor().getLogin(), respuesta.getSolucion());
	}

}
