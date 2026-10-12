package com.thomasalves.library.resource.exception;

import com.thomasalves.library.service.exception.ObjectNotFoundException;
import com.thomasalves.library.service.exception.ResourceConflictException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> idNotFound(ObjectNotFoundException e , HttpServletRequest request) {

        StandardError st = new StandardError();
        st.setError("Object not found");
        st.setTimestamp(Instant.now());
        st.setStatus(HttpStatus.NOT_FOUND.value());
        st.setPath(request.getRequestURI());
        st.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(st);
        }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<StandardError> resourceConflict(ResourceConflictException e, HttpServletRequest request) {
        StandardError st = new StandardError();
        st.setError("Resource conflict");
        st.setTimestamp(Instant.now());
        st.setStatus(HttpStatus.CONFLICT.value());
        st.setPath(request.getRequestURI());
        st.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(st);
    }

}
