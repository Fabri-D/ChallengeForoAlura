package alura.foro.api.domain.curso;


public record DatosListaCurso(Long id, String nombre, String categoria) {

    public DatosListaCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }

}
