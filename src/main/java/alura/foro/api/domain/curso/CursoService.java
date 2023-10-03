package alura.foro.api.domain.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alura.foro.api.infra.errores.ValidacionDeIntegridad;

@Service
public class CursoService {
	
	@Autowired
	CursoRepository cursoRepository;
	
	
	public Curso getCursoByNombre (String nombre) {
		
		var totalDeCursos = cursoRepository.findAll();
		
		for (Curso curso: totalDeCursos) {
			if (curso.getNombre().equals(nombre)==true) {
				return curso;
			}
		} 
		return null;
	}
	
}
