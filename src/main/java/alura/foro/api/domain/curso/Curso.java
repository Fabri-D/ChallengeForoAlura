package alura.foro.api.domain.curso;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import alura.foro.api.domain.respuesta.Respuesta;
import alura.foro.api.domain.topico.Topico;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity(name = "Curso")
@Table(name = "cursos")
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String categoria;
	
	@OneToMany(mappedBy = "curso", cascade=CascadeType.ALL)
    private List<Topico> topicos = new ArrayList<>();

	public Curso(String nombre, String categoria) {
		this.nombre = nombre;
		this.categoria = categoria;
	}
	
	public Curso(DatosAgendarCurso datos) {
        this.nombre = datos.nombre();
        this.categoria = datos.categoria();

    }
	
	public void actualizarInformaciones(DatosActualizacionCurso datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.categoria() != null) {
            this.categoria = datos.categoria();
        }

    }
	
	

}
