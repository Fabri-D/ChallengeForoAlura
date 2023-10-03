package alura.foro.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import alura.foro.api.domain.curso.Curso;
import alura.foro.api.domain.curso.CursoRepository;
import alura.foro.api.domain.curso.DatosActualizacionCurso;
import alura.foro.api.domain.curso.DatosAgendarCurso;
import alura.foro.api.domain.curso.DatosDetalleCurso;
import alura.foro.api.domain.curso.DatosListaCurso;
import alura.foro.api.domain.respuesta.DatosDetalleRespuesta;
import alura.foro.api.domain.respuesta.DatosListaRespuesta;
import alura.foro.api.domain.respuesta.Respuesta;
import alura.foro.api.domain.respuesta.RespuestaRepository;
import alura.foro.api.domain.topico.DatosActualizacionTopico;
import alura.foro.api.domain.topico.DatosAgendarTopico;
import alura.foro.api.domain.topico.DatosDetalleTopico;
import alura.foro.api.domain.topico.DatosListaTopico;
import alura.foro.api.domain.topico.Topico;
import alura.foro.api.domain.topico.TopicoRepository;
import alura.foro.api.domain.topico.TopicoService;
import alura.foro.api.domain.usuarios.UsuarioService;
import alura.foro.api.infra.errores.ValidacionDeIntegridad;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/topicos")
public class TopicoController {
	
	@Autowired
    private TopicoRepository topicoRepository;
	
	@Autowired
    private UsuarioService usuarioService;
	
	@Autowired
	private TopicoService topicoService;
	
	@Autowired
	private RespuestaRepository respuestaRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity registrar(@RequestBody @Valid DatosAgendarTopico datos, UriComponentsBuilder uriBuilder) {
	    try {
	    	if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
	            return ResponseEntity.badRequest().body("Ya existe un tópico con este título y mensaje.");
	        }
	        var topico = new Topico();
	        topicoService.agendarTopico(datos, topico);
	        var usuario = usuarioService.getUsuarioAutenticado();
	        topico.setAutor(usuario);
	        topicoRepository.save(topico);

	        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
	        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
	    } catch (ValidacionDeIntegridad ex) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Error de integridad: " + ex.getMessage());
	    }
	}
    
    @DeleteMapping("/{id}")
    @Transactional
    @Query("""
    		select * from Topico
    		where id=:id
    		""")
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
    	Topico topico = topicoRepository.findById(id).get();
    	topicoRepository.delete(topico);
    	System.out.println("Se eliminó 1 topico");
    	return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listar(@PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
    	var page = topicoRepository.findAll(paginacion).map(DatosListaTopico::new);
        return ResponseEntity.ok(page);
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity<?> actualizar(@RequestBody @Valid DatosActualizacionTopico datos) {
        // Obtener una referencia al tópico por su ID
        var topico = topicoRepository.getReferenceById(datos.id());

        // Realiza la validación personalizada para asegurarse de que no haya conflictos
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con este título y mensaje.");
        }

        // Actualizar las informaciones del tópico utilizando el servicio
        topicoService.actualizarInformaciones(datos, topico);

        // Devuelve una respuesta exitosa con el tópico actualizado
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }
    
    @PutMapping("/topicos/{id}")
    @Transactional
    public ResponseEntity<?> actualizarPorId(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos) {
        // Obtener una referencia al tópico por su ID
        var topico = topicoRepository.getReferenceById(id);

        // Realiza la validación personalizada para asegurarse de que no haya conflictos
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con este título y mensaje.");
        }

        // Actualizar las informaciones del tópico utilizando el servicio
        topicoService.actualizarInformaciones(datos, topico);

        // Devuelve una respuesta exitosa con el tópico actualizado
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }
    
   
    @GetMapping("/{id}")
    public ResponseEntity<Page<Object>> obtenerRespuestasPaginadas(@PathVariable Long id,
                                                                    @PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        List<Respuesta> respuestas = respuestaRepository.findByTopicoId(id);

        List<Object> resultados = new ArrayList<>();

        // Obtener el tópico correspondiente
        Topico topico = topicoRepository.findById(id).orElse(null);

        if (topico != null) {
            DatosListaTopico datosListaTopico = new DatosListaTopico(topico);
            resultados.add(datosListaTopico);
        }

        int pageSize = paginacion.getPageSize();
        int pageOffset = paginacion.getPageNumber() * pageSize;

        List<DatosListaRespuesta> datosListaRespuesta = respuestas.stream()
                .map(DatosListaRespuesta::new)
                .collect(Collectors.toList());

        if (pageOffset < datosListaRespuesta.size()) {
            int fromIndex = Math.min(pageOffset, datosListaRespuesta.size());
            int toIndex = Math.min(pageOffset + pageSize, datosListaRespuesta.size());
            resultados.addAll(datosListaRespuesta.subList(fromIndex, toIndex));
        }

        Page<Object> pageResult = new PageImpl<>(resultados, paginacion, resultados.size());

        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

}
