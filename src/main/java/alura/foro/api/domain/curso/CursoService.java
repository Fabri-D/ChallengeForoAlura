package alura.foro.api.domain.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alura.foro.api.infra.errores.ValidacionDeIntegridad;

@Service
public class CursoService {
	
	@Autowired
	CursoRepository cursoRepository;
	
	Curso curso = null;
	public Curso getCursoByNombre (String nombre) {
		var totalDeCursos = cursoRepository.findAll();
		
		for (Curso curso: totalDeCursos) {
			if (curso.getNombre().equals(nombre)==true) {
				return curso;
			}
		}
		if (curso==null) {
			throw new ValidacionDeIntegridad("No se encontró ningún curso con ese nombre");
		}
		return null;
	}
	
}
