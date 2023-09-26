package com.alura.foro.modelo;

import java.time.LocalDateTime;



import alura.foro.api.domain.usuarios.Usuario;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Respuesta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mensaje;
	private Topico topico;
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	private Usuario autor;
	private Boolean solucion = false;

	
}
