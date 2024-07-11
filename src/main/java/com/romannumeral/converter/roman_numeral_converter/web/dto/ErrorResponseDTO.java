package com.romannumeral.converter.roman_numeral_converter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Error Response DTO for RomanNumeralConverterController
 */
@AllArgsConstructor
@Data
public class ErrorResponseDTO
{
    private int statusCode;
    private String errorMessage;
}
