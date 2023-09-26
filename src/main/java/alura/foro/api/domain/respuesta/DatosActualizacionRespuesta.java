package alura.foro.api.domain.respuesta;

import java.time.LocalDateTime;
import java.util.List;

import alura.foro.api.domain.curso.Curso;
import alura.foro.api.domain.respuesta.Respuesta;
import alura.foro.api.domain.usuarios.Usuario;
import jakarta.validation.constraints.NotNull;

public record DatosActualizacionRespuesta(
		
		@NotNull
        Long id,
        String mensaje,
        Long topico,
        LocalDateTime fechaCreacion,        
        Boolean solucion
		
		) {

}
