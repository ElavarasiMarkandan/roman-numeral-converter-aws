package com.romannumeral.converter.roman_numeral_converter.web.error;

/**
 * Error message constants used in ErrorResponseDTO for different error scenarios
 */
public class ErrorMessage
{
    public static final String INVALID_DATA = "Invalid input, out of range. Enter an integer value in the range from 1 to 255";
    public static final String INPUT_TYPE_MISMATCH = "Invalid input type, enter an integer value in the range from 1 to 255";
    public static final String INTERNAL_SERVER_ERROR = "Oops! Something went wrong, please try again later";
}
