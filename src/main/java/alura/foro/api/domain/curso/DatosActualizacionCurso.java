package alura.foro.api.domain.curso;

import jakarta.validation.constraints.NotNull;


public record DatosActualizacionCurso(
		
		@NotNull
        Long id,
        String nombre,
        String categoria
        
		
		) 
{

}

