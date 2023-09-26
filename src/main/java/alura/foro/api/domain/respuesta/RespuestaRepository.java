package alura.foro.api.domain.respuesta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import alura.foro.api.domain.topico.Topico;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
	
	// Método personalizado para buscar respuestas por ID de Tópico
    @Query("SELECT r FROM Respuesta r WHERE r.topico.id = :idTopico")
    List<Respuesta> findByTopicoId(@Param("idTopico") Long idTopico);
	
}
