package alura.foro.api.domain.topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import alura.foro.api.domain.curso.Curso;
import alura.foro.api.domain.curso.CursoRepository;
import alura.foro.api.domain.curso.CursoService;
import alura.foro.api.domain.curso.DatosActualizacionCurso;
import alura.foro.api.domain.curso.DatosAgendarCurso;
import alura.foro.api.domain.respuesta.Respuesta;
import alura.foro.api.domain.respuesta.RespuestaRepository;
import alura.foro.api.domain.usuarios.Usuario;
import alura.foro.api.domain.usuarios.UsuarioRepository;
import alura.foro.api.domain.usuarios.UsuarioService;
import alura.foro.api.infra.errores.ValidacionDeIntegridad;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Topico")
@Table(name = "topicos")
public class Topico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensaje;
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	private StatusTopico status = StatusTopico.NO_RESPONDIDO;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario autor;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "curso_id")
	private Curso curso;
	
	@OneToMany(mappedBy = "topico", cascade=CascadeType.ALL)
    private List<Respuesta> respuestas = new ArrayList<>();
	
	@Autowired
	CursoService cursoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	CursoRepository cursoRepository;
	
	@Autowired
	RespuestaRepository respuestaRepository;

	public Topico(String titulo, String mensaje, Curso curso, Usuario usuario) {
		this.titulo = titulo;
		this.mensaje = mensaje;
		this.curso = curso;
		this.autor = usuario;
	}
	
	public Topico(DatosAgendarTopico datos) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        var curso = cursoService.getCursoByNombre(datos.curso());
        this.curso = curso;
        this.autor = usuarioService.getUsuarioAutenticado();

    }

	public void actualizarInformaciones(@Valid DatosActualizacionTopico datos) {
		if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
        if (datos.fechaCreacion() != null) {
            this.fechaCreacion = datos.fechaCreacion();
        }
//        if (datos.autor() != null) {
//        	try {
//                Long autorId = Long.valueOf(datos.autor());
//                Usuario autor = usuarioRepository.findById(autorId).orElse(null);
//                
//                if (autor != null) {
//                    this.autor = autor;
//                } else {
//                    throw new ValidacionDeIntegridad("El id de autor no fue encontrado");
//                }
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
        if (datos.curso() != null) {
            // Busca el curso por su nombre en el repositorio
            Curso cursoPorNombre = cursoRepository.findByNombre(datos.curso());
            
            if (cursoPorNombre != null) {
                this.curso = cursoPorNombre;
            } else {
                throw new ValidacionDeIntegridad("El nombre del curso no fue encontrado");
            }
        }
        if (datos.idsRespuestas() != null) {
            List<Long> respuestaIds = datos.idsRespuestas();
            List<Respuesta> respuestas = respuestaIds.stream()
                    .map(id -> (Respuesta) respuestaRepository.findById(id).orElse(null))
                    .filter(respuesta -> respuesta != null)
                    .collect(Collectors.toList());
            
            this.respuestas = respuestas;
        }
		
	}
	
	String idsRespuestasComoStr() {
		List<Long> idsRespuestas = this.getRespuestas().stream()
		        .map(respuesta -> respuesta.getId())
		        .collect(Collectors.toList());
	
		String idsComoString = idsRespuestas.stream()
		        .map(Object::toString)
		        .collect(Collectors.joining(","));
	
		return idsComoString;
	}

}
