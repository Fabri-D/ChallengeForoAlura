package alura.foro.api.controller;

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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import alura.foro.api.domain.respuesta.DatosActualizacionRespuesta;
import alura.foro.api.domain.respuesta.DatosAgendarRespuesta;
import alura.foro.api.domain.respuesta.DatosDetalleRespuesta;
import alura.foro.api.domain.respuesta.DatosListaRespuesta;
import alura.foro.api.domain.respuesta.Respuesta;
import alura.foro.api.domain.respuesta.RespuestaRepository;
import alura.foro.api.domain.respuesta.RespuestaService;
import alura.foro.api.domain.topico.DatosActualizacionTopico;
import alura.foro.api.domain.topico.DatosAgendarTopico;
import alura.foro.api.domain.topico.DatosDetalleTopico;
import alura.foro.api.domain.topico.DatosListaTopico;
import alura.foro.api.domain.topico.Topico;
import alura.foro.api.domain.topico.TopicoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/topico/{idTopico}/respuestas")
public class RespuestaController {
	
	@Autowired
    private RespuestaService respuestaService;
	
	@Autowired
	RespuestaRepository respuestaRepository;
	
	@Autowired
	TopicoRepository topicoRepository;

    @GetMapping
    public ResponseEntity<Page<DatosListaRespuesta>> listar(@PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion, @PathVariable Long idTopico) {
        List<Respuesta> respuestas = respuestaService.obtenerRespuestasPorIdTopico(idTopico);
        
        // Crear una instancia de Page a partir de la lista de respuestas
        Page<Respuesta> pageRespuestas = new PageImpl<>(respuestas, paginacion, respuestas.size());

        // Mapear la página de respuestas a DatosListaRespuesta
        Page<DatosListaRespuesta> pageDatosRespuesta = pageRespuestas.map(DatosListaRespuesta::new);

        return ResponseEntity.ok(pageDatosRespuesta);
    }

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosAgendarRespuesta datos, 
                                    @PathVariable Long idTopico, 
                                    UriComponentsBuilder uriBuilder) {
        var respuesta = new Respuesta(datos, idTopico);

        // Asociar la respuesta con el tópico encontrado
        //respuesta.setTopico(topico);
       
        respuestaRepository.save(respuesta);

        var uri = uriBuilder.path("/topico/{idTopico}/respuestas/{id}")
                            .buildAndExpand(idTopico, respuesta.getId())
                            .toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleRespuesta(respuesta));
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    @Query("""
    		select * from Respuesta
    		where id=:id
    		""")
    public ResponseEntity eliminarRespuesta(@PathVariable Long id) {
    	Respuesta respuesta = respuestaRepository.findById(id).get();
    	respuestaRepository.delete(respuesta);
    	System.out.println("Se eliminó 1 respuesta");
    	return ResponseEntity.noContent().build();
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionRespuesta datos) {
        var respuesta = respuestaRepository.getReferenceById(datos.id());
        respuesta.actualizarInformaciones(datos);

        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

}
