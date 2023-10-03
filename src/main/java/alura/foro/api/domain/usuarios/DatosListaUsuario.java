package alura.foro.api.domain.usuarios;

import java.time.LocalDateTime;

import alura.foro.api.domain.respuesta.Respuesta;

public record DatosListaUsuario(Long id, String login, String email) {
	public DatosListaUsuario(Usuario usuario) {
		this(usuario.getId(), usuario.getLogin(), usuario.getEmail());
	}

}