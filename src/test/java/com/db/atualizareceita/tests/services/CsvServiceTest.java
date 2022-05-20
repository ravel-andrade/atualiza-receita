package com.db.atualizareceita.tests.services;
import com.db.atualizareceita.model.CsvData;
import com.db.atualizareceita.tests.CsvService;
import com.db.atualizareceita.tests.ReceitaService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class CsvServiceTest {
    CsvService csvService = new CsvService(new ReceitaService());
    String VALID_CSV = "src/test/java/resources/validCsv.csv";
    String INVALID_CSV = "src/test/java/resources/invalidCsv.csv";
    @Test
    public void shouldReturnTrueWhenValidateCsv(){
        boolean result = csvService.csvFileIsValid(VALID_CSV);
        assertEquals(true, result);
    }

    @Test
    public void validateCsvShouldReturnFalseWhenHeadersAreInvalid(){
        boolean result = csvService.csvFileIsValid(INVALID_CSV);
        assertEquals(false, result);
    }

    @Test
    public void validateCsvShouldReturnFalseWhenFileDoesntExist(){
        boolean result = csvService.csvFileIsValid("fake.csv");
        assertEquals(false, result);
    }

    @Test
    public void shouldExtractDataFromCsv(){
        List<CsvData> expectedResult = buildExpectedResult();
        List<CsvData> result = csvService.extractDataFromCsv(VALID_CSV);
        assertEquals(expectedResult, result);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotExtractDataFromInvalidCsv(){
        csvService.extractDataFromCsv(INVALID_CSV);
    }

    private List<CsvData> buildExpectedResult() {
        List<CsvData> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CsvData("101", "1223", 12.0, "I"));
        expectedResultList.add(new CsvData("0101", "122256", 100.0, "A"));
        return expectedResultList;
    }


}
