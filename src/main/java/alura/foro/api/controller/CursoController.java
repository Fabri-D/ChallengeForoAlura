package alura.foro.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


import alura.foro.api.domain.curso.Curso;
import alura.foro.api.domain.curso.CursoRepository;
import alura.foro.api.domain.curso.DatosActualizacionCurso;
import alura.foro.api.domain.curso.DatosAgendarCurso;
import alura.foro.api.domain.curso.DatosDetalleCurso;
import alura.foro.api.domain.curso.DatosListaCurso;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/cursos")
public class CursoController {

          
    @Autowired
    private CursoRepository repository;
    
    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosAgendarCurso datos, UriComponentsBuilder uriBuilder) {
        var curso = new Curso(datos);
        repository.save(curso);

        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleCurso(curso));
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    @Query("""
    		select * from Curso
    		where id=:id
    		""")
    public ResponseEntity eliminarCurso(@PathVariable Long id) {
    	Curso curso = repository.findById(id).get();
    	repository.delete(curso);
    	System.out.println("Se eliminó 1 curso");
    	return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<Page<DatosListaCurso>> listar(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacion) {
    	var page = repository.findAll(paginacion).map(DatosListaCurso::new);
        return ResponseEntity.ok(page);
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionCurso datos) {
        var curso = repository.getReferenceById(datos.id());
        curso.actualizarInformaciones(datos);

        return ResponseEntity.ok(new DatosDetalleCurso(curso));
    }
	

}
