package com.romannumeral.converter.roman_numeral_converter.acceptance;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.romannumeral.converter.roman_numeral_converter.web.dto.ErrorResponseDTO;
import com.romannumeral.converter.roman_numeral_converter.web.dto.RomanNumeralResponseDTO;
import com.romannumeral.converter.roman_numeral_converter.web.error.ErrorMessage;

// ATs can be integrated as one of the step into the CI/CD pipeline with test cases, which confirms application starts
// and run without any error and can be deployed to next stage
public class IntegerToRomanNumeralConverterAT
{
    // AT #1 - Validate response content type is application/json
    @Test
    public void testContentTypeIsApplicationJson() throws IOException
    {
        String expectedContentType = "application/json";
        HttpUriRequest request = new HttpGet("http://localhost:8080/romannumeral?query=200");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertEquals(expectedContentType, ContentType.getOrDefault(response.getEntity()).getMimeType());
    }

    // AT #2 - Happy path case to convert valid integer to roman numeral
    @Test
    public void testRomanNumeralConversion_Success() throws IOException
    {
        HttpUriRequest request = new HttpGet("http://localhost:8080/romannumeral?query=255");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        RomanNumeralResponseDTO romanNumeralResponseDTO = parseFromResponse(response,
                                                                            RomanNumeralResponseDTO.class);

        assertEquals("255", romanNumeralResponseDTO.getInput());
        assertEquals("CCLV", romanNumeralResponseDTO.getOutput());
    }

    // AT #3 - Error case to validate out of range conversions. Valid range 1 to 255
    @Test
    public void testRomanNumeralConversion_OutOfRange_Error() throws IOException
    {
        HttpUriRequest request = new HttpGet("http://localhost:8080/romannumeral?query=256");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        ErrorResponseDTO errorResponseDTO = parseFromResponse(response, ErrorResponseDTO.class);

        assertEquals(400, errorResponseDTO.getStatusCode());
        assertTrue(errorResponseDTO.getErrorMessage().contains(ErrorMessage.INVALID_DATA));
    }

    // AT #4 - Error case to validate invalid data type inputs. Valid data type int.
    @Test
    public void testRomanNumeralConversion_InvalidDataType_Error() throws IOException
    {
        HttpUriRequest request = new HttpGet("http://localhost:8080/romannumeral?query=xx");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        ErrorResponseDTO errorResponseDTO = parseFromResponse(response, ErrorResponseDTO.class);

        assertEquals(400, errorResponseDTO.getStatusCode());
        assertTrue(errorResponseDTO.getErrorMessage().contains(ErrorMessage.INPUT_TYPE_MISMATCH));
    }

    // helper method to parse json response body from Httpresponse
    public static <T> T parseFromResponse(HttpResponse response, Class<T> clazz)
            throws IOException
    {
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, clazz);
    }

}
