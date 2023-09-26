package alura.foro.api.domain.topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import alura.foro.api.domain.curso.Curso;
import alura.foro.api.domain.respuesta.Respuesta;
import alura.foro.api.domain.usuarios.Usuario;

public record DatosListaTopico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, StatusTopico status, String autor, String curso, String idRespuestas) {
	public DatosListaTopico(Topico topico) {
		this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), 
				topico.getStatus(), topico.getAutor().getLogin(), topico.getCurso().getNombre(), topico.idsRespuestasComoStr());
	}

}

    
