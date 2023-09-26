package alura.foro.api.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DatosAgendarTopico(
		
		@NotBlank
		String titulo,
		@NotBlank
		String mensaje,
		@NotBlank
		String curso
		
		
		
		
		) {

}
