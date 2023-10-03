package alura.foro.api.domain.topico;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueTopicValidator.class)
public @interface UniqueTopic {

    String message() default "Ya existe un tópico con este título y mensaje.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

