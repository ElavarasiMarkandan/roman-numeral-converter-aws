package com.romannumeral.converter.roman_numeral_converter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response DTO for RomanNumeralConverterController
 */
@AllArgsConstructor
@Data
public class RomanNumeralResponseDTO
{
    private String input;
    private String output;
}
