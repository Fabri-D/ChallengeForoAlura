package alura.foro.api.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long>{

	boolean existsByTituloAndMensaje(String titulo, String mensaje);
	
}
