CREATE TABLE topicos (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    usuario_id BIGINT,
    curso_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    FOREIGN KEY (curso_id) REFERENCES cursos (id)
);

CREATE TABLE respuestas (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    mensaje TEXT,
    topico_id BIGINT,
    fecha_creacion TIMESTAMP NOT NULL,
    usuario_id BIGINT,
    solucion BOOLEAN NOT NULL,
    FOREIGN KEY (topico_id) REFERENCES topicos (id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);