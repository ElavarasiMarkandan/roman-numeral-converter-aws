package com.romannumeral.converter.roman_numeral_converter.service;

import org.springframework.stereotype.Service;

/**
 * Implements IntegerToRomanNumeralConverterService to provide logic for converting integer to roman numeral
 */
@Service
public class IntegerToRomanNumeralConverterServiceImpl implements IntegerToRomanNumeralConverterService
{
    private static final int[] numbers = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
    private static final String[] romanNumerals = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM",
            "M" };

    /**
     * This method takes int as input and returns String Roman numeral representation as output
     * Static variables are defined for numbers & roman numerals
     * Time Complexity and Space Complexity is O(1) - Because we know the finite number of iterations and memory is constant
     */
    @Override public String convertIntegerToRomanNumeral(int input)
    {
        StringBuilder romanNumeralBuilder = new StringBuilder();
        int times;

        //iterate over until the numbers array length to 0
        for (int i = numbers.length - 1; i >= 0; i--)
        {
            times = input / numbers[i];// check whether the given input fits in the number array value
            input %= numbers[i];// This condition results the remainder, once the given input fits in the number array value
            //loop until the input fits in the given number range and select the appropriate roman numeral accordingly
            while (times > 0)
            {
                romanNumeralBuilder.append(romanNumerals[i]);
                times--;
            }
        }
        return romanNumeralBuilder.toString();
    }
}
