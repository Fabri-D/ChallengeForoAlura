package alura.foro.api.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosAgendarCurso(
		
		
		@NotBlank
		String nombre,
		@NotBlank
		String categoria
		
		
		) {

}
