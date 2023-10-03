package alura.foro.api.domain.respuesta;

import java.time.LocalDateTime;

public record DatosListaRespuestaConTopico(Long id, String mensaje, LocalDateTime fechaCreacion,
        String autor, Boolean solucion, Long topicoId, String topicoTitulo) {

    public DatosListaRespuestaConTopico(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaCreacion(),
                respuesta.getAutor().getLogin(), respuesta.getSolucion(),
                respuesta.getTopico().getId(), respuesta.getTopico().getTitulo());
    }
}
