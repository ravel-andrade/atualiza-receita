package com.db.atualizareceita.validator;

import com.db.atualizareceita.fileMenager.FileMenager;
import com.db.atualizareceita.validator.Validator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidatorTest {

    String VALID_CSV = "src/test/java/resources/validCsv.csv";
    String INVALID_CSV = "src/test/java/resources/invalidCsv.csv";

    FileMenager menager = new FileMenager();
    Validator csvValidator = new Validator(menager);

    @Test
    public void shouldReturnTrueWhenValidateCsv(){
        boolean result = csvValidator.csvFileIsValid(VALID_CSV);
        assertEquals(true, result);
    }

    @Test
    public void validateCsvShouldReturnFalseWhenHeadersAreInvalid(){
        boolean result = csvValidator.csvFileIsValid(INVALID_CSV);
        assertEquals(false, result);
    }

    @Test
    public void validateCsvShouldReturnFalseWhenFileDoesntExist(){
        boolean result = csvValidator.csvFileIsValid("fake.csv");
        assertEquals(false, result);
    }
}
