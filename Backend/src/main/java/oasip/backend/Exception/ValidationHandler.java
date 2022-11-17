package oasip.backend.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField() , error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName() , error.getDefaultMessage());
        }
        final Map<String, Object> body = new LinkedHashMap<>();
        final String path = request.getDescription(false);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", HttpStatus.BAD_REQUEST);
        body.put("message", errors);
//        body.put("fieldErrors", errors);
        body.put("path", path.substring(4));
        return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation( ConstraintViolationException ex, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
            errors.put(error.getPropertyPath().toString(), error.getMessage());
        }
        final Map<String, Object> body = new LinkedHashMap<>();
        final String path = request.getDescription(false);
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST);
        body.put("message", errors);
//        body.put("fieldErrors", errors);
        body.put("path", path.substring(4));
        return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);


    }

}
