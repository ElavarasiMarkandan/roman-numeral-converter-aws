package com.romannumeral.converter.roman_numeral_converter.web.controller;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.romannumeral.converter.roman_numeral_converter.service.IntegerToRomanNumeralConverterService;
import com.romannumeral.converter.roman_numeral_converter.web.dto.RomanNumeralResponseDTO;
import com.romannumeral.converter.roman_numeral_converter.web.error.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Spring Rest Controller to create REST api for converting Integer to RomanNumeral
 */
@RestController
@RequestMapping(value = "/romannumeral")
@Tag(name = "IntegerToRomanNumeralConverterController", description = "REST api to convert Integer to Roman Numeral")
@Validated
public class IntegerToRomanNumeralConverterController
{
    @Autowired
    IntegerToRomanNumeralConverterService integerToRomanNumeralConverterService;

    /**
     * GET method to convert int(range 1 to 255) to Roman Numeral
     *
     * @param input
     * @return RomanNumeralResponseDTO/ErrorResponseDTO
     * exceptions handled by RestControllerAdvice
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "GET endpoint that accepts int as input and response with the converted romannumeral in JSON format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = ErrorMessage.INPUT_TYPE_MISMATCH),
            @ApiResponse(responseCode = "400", description = ErrorMessage.INVALID_DATA),
            @ApiResponse(responseCode = "500", description = ErrorMessage.INTERNAL_SERVER_ERROR)
    })
    public RomanNumeralResponseDTO convertIntegerToRomanNumeral(@RequestParam("query") @Min(value = 1)
                                                                @Max(value = 255) int input)
    {
        String romanNumeral = integerToRomanNumeralConverterService.convertIntegerToRomanNumeral(input);
        RomanNumeralResponseDTO romanNumeralResponseDTO = new RomanNumeralResponseDTO(String.valueOf(input),
                                                                                      romanNumeral);
        return romanNumeralResponseDTO;
    }
}
