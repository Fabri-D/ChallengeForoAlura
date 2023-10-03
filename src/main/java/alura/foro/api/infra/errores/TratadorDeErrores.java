package alura.foro.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e){
       return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        // Aquí puedes personalizar el mensaje de error según tus necesidades
        String errorMessage = "Acceso denegado: " + e.getMessage();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }

}