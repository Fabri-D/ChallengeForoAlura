package alura.foro.api.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    Boolean existsByNombre(String nombre);
    
    Curso findByNombre(String nombre);

}
