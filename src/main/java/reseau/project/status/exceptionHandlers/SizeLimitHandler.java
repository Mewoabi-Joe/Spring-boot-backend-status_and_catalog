package reseau.project.status.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class SizeLimitHandler{
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
    public ResponseEntity<Object> handleMultipartException(MultipartException ex) {
        Map<String, String> subErrors = new HashMap<>();

            subErrors.put("error",ex.getMessage());
        return new ResponseEntity<>(subErrors,HttpStatus.BAD_REQUEST);
    }
}
