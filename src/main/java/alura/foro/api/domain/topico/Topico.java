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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import alura.foro.api.domain.curso.Curso;
import alura.foro.api.domain.curso.CursoRepository;
import alura.foro.api.domain.curso.CursoService;
import alura.foro.api.domain.curso.DatosActualizacionCurso;
import alura.foro.api.domain.curso.DatosAgendarCurso;
import alura.foro.api.domain.publicacion.Publicacion;
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
@UniqueTopic
public class Topico extends Publicacion {
	
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
	
	@PreUpdate
    private void preUpdate() {
        // Verifica si hay respuestas asociadas
        if (!respuestas.isEmpty() && !this.status.equals(StatusTopico.CERRADO) && !this.status.equals(StatusTopico.SOLUCIONADO) ) {
            status = StatusTopico.NO_SOLUCIONADO;
        }
    }

	public Topico(String titulo, String mensaje, Curso curso, Usuario usuario) {
		this.titulo = titulo;
		this.mensaje = mensaje;
		this.curso = curso;
		this.autor = usuario;
	}
		

}
