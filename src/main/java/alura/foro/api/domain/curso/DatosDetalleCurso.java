package alura.foro.api.domain.curso;

import alura.foro.api.domain.curso.*;

public record DatosDetalleCurso(Long id, String nombre, String categoria) {
	public DatosDetalleCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }

}
