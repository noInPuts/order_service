package cphbusiness.noinputs.order_service.main.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import cphbusiness.noinputs.order_service.main.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MismatchedInputExceptionHandler {

    // This exception handler is used to catch errors for mismatched input in JSON requests

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {

        // If the cause of the exception is a MismatchedInputException, return a 400 error
        if (ex.getCause() instanceof MismatchedInputException) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("Mismatch error (Check if JSON request format is correct)");
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
        }

        // If the cause of the exception is not a MismatchedInputException, return a 400 error
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
