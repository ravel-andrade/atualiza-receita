package com.db.atualizareceita.validator;

import com.db.atualizareceita.fileMenager.FileMenager;
import com.db.atualizareceita.validator.Validator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidatorTest {
    Validator csvValidator = new Validator();

    @Test
    public void shouldReturnTrueWhenValidateCsv() {
        boolean result = csvValidator.csvHeadersAreValid(getValidHeaders());
        assertEquals(true, result);
    }

    @Test
    public void validateCsvShouldReturnFalseWhenHeadersAreInvalid() {
        boolean result = csvValidator.csvHeadersAreValid(getInvalidHeaders());
        assertEquals(false, result);
    }


    private String[] getValidHeaders() {
        return new String[]{"agencia", "conta", "saldo", "status"};
    }

    private String[] getInvalidHeaders() {
        return new String[]{"agencia", "conta", "saldos", "status"};
    }
}
