package alura.foro.api.domain.publicacion;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Publicacion {

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
}

