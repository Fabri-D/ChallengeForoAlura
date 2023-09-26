package alura.foro.api.domain.topico;

import java.time.LocalDateTime;
import java.util.List;

import alura.foro.api.domain.curso.Curso;
import alura.foro.api.domain.respuesta.Respuesta;
import alura.foro.api.domain.usuarios.Usuario;
import jakarta.validation.constraints.NotNull;

public record DatosActualizacionTopico(
		
		
		@NotNull
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        String curso,
        List<Long> idsRespuestas
		
		
		) {

}
