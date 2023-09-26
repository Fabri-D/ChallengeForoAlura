package alura.foro.api.domain.respuesta;

import jakarta.validation.constraints.NotBlank;

public record DatosAgendarRespuesta(
		
		@NotBlank
		String mensaje,
		@NotBlank
		String topico
		
		) {

}
