package alura.foro.api.domain.topico;

import java.time.LocalDateTime;
import java.util.List;

public record DatosActualizacionTopicoSinId(
	    String titulo,
	    String mensaje,
	    LocalDateTime fechaCreacion,
	    StatusTopico status,
	    String curso,
	    List<Long> idsRespuestas
	) {
	}
