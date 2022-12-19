package com.db.atualizareceita.validator;

import com.db.atualizareceita.fileMenager.FileMenager;
import com.db.atualizareceita.validator.Validator;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.assertEquals;

public class ValidatorTest {

    String VALID_CSV = "src/test/java/resources/validCsv.csv";
    String INVALID_CSV = "src/test/java/resources/invalidCsv.csv";
    Validator csvValidator = new Validator();

    @Test
    public void shouldReturnTrueWhenValidateCsv(){
        boolean result = csvValidator.csvFileIsValid(getValidHeaders());
        assertEquals(true, result);
    }

    @Test
    public void validateCsvShouldReturnFalseWhenHeadersAreInvalid(){
        boolean result = csvValidator.csvFileIsValid(getInvalidHeaders());
        assertEquals(false, result);
    }

    private String[] getValidHeaders(){
        return new String[]{"agencia","conta","saldo","status"};
    }
    private String[] getInvalidHeaders(){
        return new String[]{"agencia","conta","saldos","status"};
    }
}
