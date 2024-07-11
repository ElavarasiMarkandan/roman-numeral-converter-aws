package com.romannumeral.converter.roman_numeral_converter.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * Unit tests - To verify integer to roman numeral conversion logic.
 */

class IntegerToRomanNumeralConverterServiceImplTest
{
    @Test
    void testIntegerToRomanNumeralConversion_Success()
    {
        IntegerToRomanNumeralConverterServiceImpl integerToRomanNumeralConverterService = new IntegerToRomanNumeralConverterServiceImpl();
        assertEquals(integerToRomanNumeralConverterService.convertIntegerToRomanNumeral(1), "I");
        assertEquals(integerToRomanNumeralConverterService.convertIntegerToRomanNumeral(10), "X");
        assertEquals(integerToRomanNumeralConverterService.convertIntegerToRomanNumeral(50), "L");
        assertEquals(integerToRomanNumeralConverterService.convertIntegerToRomanNumeral(100), "C");
        assertEquals(integerToRomanNumeralConverterService.convertIntegerToRomanNumeral(255), "CCLV");
    }
}
