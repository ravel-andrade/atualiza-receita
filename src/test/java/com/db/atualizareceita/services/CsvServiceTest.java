package com.db.atualizareceita.services;

import com.db.atualizareceita.model.CsvData;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class CsvServiceTest {
    CsvService csvService = new CsvService(new ReceitaService());

    @Test
    public void shouldReturnTrueWhenValidateCsv(){
        boolean result = csvService.csvFileIsValid("src/test/java/resources/validCsv.csv");
        assertEquals(true, result);
    }

    @Test
    public void validateCsvShouldReturnFalseWhenHeadersAreInvalid(){
        boolean result = csvService.csvFileIsValid("src/test/java/resources/invalidCsv.csv");
        assertEquals(false, result);
    }

    @Test
    public void validateCsvShouldReturnFalseWhenFileDoesntExist(){
        boolean result = csvService.csvFileIsValid("src/test/java/resources/fake.csv");
        assertEquals(false, result);
    }

    @Test
    public void shouldExtractDataFromCsv(){
        List<CsvData> expectedResult = buildExpectedResult();
        List<CsvData> result = csvService.extractDataFromCsv("src/test/java/resources/validCsv.csv");
        assertEquals(expectedResult, result);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotExtractDataFromInvalidCsv(){
        csvService.extractDataFromCsv("src/test/java/resources/invalidCsv.csv");
    }

    private List<CsvData> buildExpectedResult() {
        List<CsvData> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CsvData("101", "1223", 12.0, "I"));
        expectedResultList.add(new CsvData("0101", "122256", 100.0, "A"));
        return expectedResultList;
    }


}
