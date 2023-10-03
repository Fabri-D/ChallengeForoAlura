package alura.foro.api.domain.topico;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alura.foro.api.domain.curso.Curso;
import alura.foro.api.domain.curso.CursoRepository;
import alura.foro.api.domain.curso.CursoService;
import alura.foro.api.domain.respuesta.Respuesta;
import alura.foro.api.domain.respuesta.RespuestaRepository;
import alura.foro.api.domain.usuarios.UsuarioService;
import alura.foro.api.infra.errores.ValidacionDeIntegridad;
import jakarta.validation.Valid;

@Service
public class TopicoService {
	
	@Autowired
	TopicoRepository topicoRepository;
	
	@Autowired
	CursoRepository cursoRepository;
	
	@Autowired
	RespuestaRepository respuestaRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	CursoService cursoService;
	
	public Curso getCursoByNombre(String nombreCurso) {
		if (nombreCurso != null) {
	        // Busca el curso por su nombre en el repositorio
	        Curso cursoPorNombre = cursoRepository.findByNombre(nombreCurso);
	        
	        if (cursoPorNombre != null) {
	            return cursoPorNombre;
	        } else {
	            throw new ValidacionDeIntegridad("El nombre del curso no fue encontrado");
	        }
	    }
		return null;
	}
	
	public List<Respuesta> getRespuestasByIdsRespuestas(List<Long> idsRespuestas) {
		List<Respuesta> respuestas = idsRespuestas.stream()
                .map(id -> (Respuesta) respuestaRepository.findById(id).orElse(null))
                .filter(respuesta -> respuesta != null)
                .collect(Collectors.toList());
		return respuestas;
	}
	

	public void agendarTopico(DatosAgendarTopico datos, Topico topico) {
		topico.setTitulo(datos.titulo());
		topico.setMensaje(datos.mensaje());
		topico.setCurso(cursoService.getCursoByNombre(datos.curso())); 
		topico.setAutor(usuarioService.getUsuarioAutenticado());  
    }
	
	public void actualizarInformaciones(@Valid DatosActualizacionTopico datos, Topico topico) {
		if (datos.titulo() != null) {
			topico.setTitulo(datos.titulo());
        }
        if (datos.mensaje() != null) {
            topico.setMensaje(datos.mensaje());
        }
        if (datos.fechaCreacion() != null) {
            topico.setFechaCreacion(datos.fechaCreacion());
        }
        if (datos.status() != null) {
            topico.setStatus(datos.status());
        }
        if (datos.curso()!=null) {
        	topico.setCurso(getCursoByNombre(datos.curso()));
        }
        
        if (datos.idsRespuestas() != null) {
        	topico.setRespuestas(getRespuestasByIdsRespuestas(datos.idsRespuestas()));
        }
		
	}
}

