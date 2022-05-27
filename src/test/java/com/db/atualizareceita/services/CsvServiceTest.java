package com.db.atualizareceita.services;
import com.db.atualizareceita.model.Account;
import com.db.atualizareceita.model.CsvData;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class CsvServiceTest {
    CsvService csvService = new CsvService();
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
        List<CsvData> expectedResult = buildACsvList();
        List<CsvData> result = csvService.extractDataFromCsv(VALID_CSV);
        assertEquals(expectedResult, result);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotExtractDataFromInvalidCsv(){
        csvService.extractDataFromCsv(INVALID_CSV);
    }

    @Test
    public void shouldSaveUpdatedIncomesInCsvFile(){
        Boolean result = csvService.saveUpdatedIncomesInCsvFile(buildACsvList(), null);
        assertEquals(true, result);
    }

    @Test
    public void shouldNotSaveUpdatedIncomesInCsvFile(){
        Boolean result = csvService.saveUpdatedIncomesInCsvFile(buildACsvList(), "invalid/destine/path");
        assertEquals(false, result);
    }

    @Test
    public void getCsvDataPathWithCsvPathOnly(){
        Map<String, String> result = csvService.getCsvDataPath(new String[]{"csv.csv"});
        assertEquals("csv.csv", result.get("csvpath"));
    }

    @Test
    public void getCsvDataPathWithCsvPathAndDestineUrl(){
        Map<String, String> result = csvService.getCsvDataPath(new String[]{"csv.csv", "path/path"});
        assertEquals("csv.csv", result.get("csvpath"));
        assertEquals("path/path", result.get("destineurl"));
    }

    private List<CsvData> buildACsvList() {
        List<CsvData> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CsvData(new Account("101", "1223", 12.0, "I")));
        expectedResultList.add(new CsvData(new Account("0101", "122256", 100.0, "A")));
        return expectedResultList;
    }


}
