package com.romannumeral.converter.roman_numeral_converter.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.romannumeral.converter.roman_numeral_converter.service.IntegerToRomanNumeralConverterService;
import com.romannumeral.converter.roman_numeral_converter.web.error.ErrorMessage;

//  Unit tests - To verify the integer to roman numeral conversion and exception handling

@WebMvcTest(IntegerToRomanNumeralConverterController.class)
class IntegerToRomanNumeralConverterControllerTest
{
    @MockBean
    IntegerToRomanNumeralConverterService integerToRomanNumeralConverterService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testIntegerToRomanNumeralConverter_Success() throws Exception
    {
        Mockito.when(integerToRomanNumeralConverterService.convertIntegerToRomanNumeral(101)).thenReturn("CI");

        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=101")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("CI")));
    }

    @Test
    void testIntegerToRomanNumeralConverter_InputOutOfRange_LessThanMinError() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=0")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString(ErrorMessage.INVALID_DATA)));
    }

    @Test
    void testIntegerToRomanNumeralConverter_InputOutOfRange_GreaterThanMaxError() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=256")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString(ErrorMessage.INVALID_DATA)));
    }

    @Test
    void testIntegerToRomanNumeralConverter_InputInvalidType() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=XX")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString(ErrorMessage.INPUT_TYPE_MISMATCH)));
    }

    @Test
    void testIntegerToRomanNumeralConverter_internalServerError() throws Exception
    {
        Mockito.when(integerToRomanNumeralConverterService.convertIntegerToRomanNumeral(200)).thenThrow(RuntimeException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=200"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString(ErrorMessage.INTERNAL_SERVER_ERROR)));
    }
}
