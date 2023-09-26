package alura.foro.api.domain.topico;

import java.time.LocalDateTime;
import java.util.List;

import alura.foro.api.domain.curso.Curso;
import alura.foro.api.domain.respuesta.Respuesta;
import alura.foro.api.domain.usuarios.Usuario;

public record DatosDetalleTopico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, StatusTopico status, String autor, String curso, String idRespuestas) {
	public DatosDetalleTopico(Topico topico) {
		this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), 
				topico.getStatus(), topico.getAutor().getLogin(), topico.getCurso().getNombre(), topico.idsRespuestasComoStr());
	}

} 