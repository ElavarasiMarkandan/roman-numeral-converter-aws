package com.romannumeral.converter.roman_numeral_converter.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.romannumeral.converter.roman_numeral_converter.web.dto.ErrorResponseDTO;
import com.romannumeral.converter.roman_numeral_converter.web.error.ErrorMessage;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/**
 * Rest Controller advice for handling below exceptions
 * 1. ConstraintViolationException
 * 2. MethodArgumentTypeMismatchException
 * 3. RuntimeException
 **/
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler
{
    /**
     * Exception handler method to handle ConstraintViolationException and returns a error response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException)
    {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(),
                                                                 ErrorMessage.INVALID_DATA);
        log.error(errorResponseDTO.toString(), constraintViolationException);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler method to handle MethodArgumentTypeMismatchException and returns a error response
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException methodArgumentTypeMismatchException)
    {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(),
                                                                 ErrorMessage.INPUT_TYPE_MISMATCH);
        log.error(errorResponseDTO.toString(), methodArgumentTypeMismatchException);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler method to handle RuntimeException and returns a error response
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException runtimeException)
    {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                                 ErrorMessage.INTERNAL_SERVER_ERROR);
        log.error(errorResponseDTO.toString(), runtimeException);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
