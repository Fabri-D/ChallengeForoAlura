package alura.foro.api.domain.topico;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueTopicValidator implements ConstraintValidator<UniqueTopic, Topico> {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void initialize(UniqueTopic constraintAnnotation) {
    }

    @Override
    public boolean isValid(Topico topico, ConstraintValidatorContext context) {
        // Realiza la validación personalizada aquí
        if (topico == null || topicoRepository == null) {
            return true; // Evita la validación si falta información
        }

        // Verifica si existe un tópico con el mismo título y mensaje
        boolean existsSimilarTopic = topicoRepository.existsByTituloAndMensaje(topico.getTitulo(), topico.getMensaje());

        // La validación es válida si no existe un tópico similar
        return !existsSimilarTopic;
    }
}
