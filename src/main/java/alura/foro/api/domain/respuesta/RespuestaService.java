package alura.foro.api.domain.respuesta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class RespuestaService {
	
	@Autowired
	RespuestaRepository respuestaRepository;
	
	public List<Respuesta> obtenerRespuestasPorIdTopico(Long idTopico) {
        return respuestaRepository.findByTopicoId(idTopico);
    }
	
}
