package poker.manager.api.rest.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import poker.manager.api.service.exceptions.PartidaFullException;
import poker.manager.api.service.exceptions.PartidaWithNoHostException;
import poker.manager.api.service.exceptions.UsuarioAlreadyInMatchException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PartidaWithNoHostException.class)
    private ResponseEntity<RestErrorMessage> partidaWithNoHostHandler(PartidaWithNoHostException exception, HttpServletRequest request) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMsg = "INTERNAL_SERVER_ERROR";
        RestErrorMessage treatedResponse = new RestErrorMessage(
                ZonedDateTime.now(ZoneId.of("Z")),
                internalServerError.value(),
                errorMsg,
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(internalServerError).body(treatedResponse);
    }
    @ExceptionHandler(PartidaFullException.class)
    private ResponseEntity<RestErrorMessage> partidaFullHandler(PartidaFullException exception, HttpServletRequest request) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMsg = "INTERNAL_SERVER_ERROR";
        RestErrorMessage treatedResponse = new RestErrorMessage(
                ZonedDateTime.now(ZoneId.of("Z")),
                internalServerError.value(),
                errorMsg,
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(internalServerError).body(treatedResponse);
    }

    @ExceptionHandler(UsuarioAlreadyInMatchException.class)
    private ResponseEntity<RestErrorMessage> UsuarioAlreadyInMatchHandler(UsuarioAlreadyInMatchException exception, HttpServletRequest request) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMsg = "INTERNAL_SERVER_ERROR";
        RestErrorMessage treatedResponse = new RestErrorMessage(
                ZonedDateTime.now(ZoneId.of("Z")),
                internalServerError.value(),
                errorMsg,
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(internalServerError).body(treatedResponse);
    }

}
