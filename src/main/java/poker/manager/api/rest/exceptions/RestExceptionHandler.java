package poker.manager.api.rest.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import poker.manager.api.service.exceptions.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private ResponseEntity<RestErrorMessage> usuarioAlreadyInMatchHandler(UsuarioAlreadyInMatchException exception, HttpServletRequest request) {
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

    @ExceptionHandler(PartidaUnableToUpdateException.class)
    private ResponseEntity<RestErrorMessage> partidaUnableToUpdateHandler(PartidaUnableToUpdateException exception, HttpServletRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        String errorMsg = "BAD_REQUEST";
        RestErrorMessage treatedResponse = new RestErrorMessage(
                ZonedDateTime.now(ZoneId.of("Z")),
                badRequest.value(),
                errorMsg,
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(badRequest).body(treatedResponse);
    }

    @ExceptionHandler(UsuarioUsernameUsedByAnotherUserException.class)
    private ResponseEntity<RestErrorMessage> usuarioUsernameUsedByAnotherUserHandler(UsuarioUsernameUsedByAnotherUserException exception, HttpServletRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        String errorMsg = "BAD_REQUEST";
        RestErrorMessage treatedResponse = new RestErrorMessage(
                ZonedDateTime.now(ZoneId.of("Z")),
                badRequest.value(),
                errorMsg,
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(badRequest).body(treatedResponse);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        String errorMsg = "BAD_REQUEST";
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ValidationFieldErrorMessage apiErrorMessage = new ValidationFieldErrorMessage(status, errors);

        return new ResponseEntity<>(apiErrorMessage, apiErrorMessage.getStatus());
    }

}
