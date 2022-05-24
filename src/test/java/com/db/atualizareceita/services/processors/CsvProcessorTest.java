package com.db.atualizareceita.services.processors;

import com.db.atualizareceita.model.CsvData;
import com.db.atualizareceita.services.processor.CsvProcessor;
import com.db.atualizareceita.services.CsvService;
import com.db.atualizareceita.services.ReceitaService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class CsvProcessorTest {
    CsvService csvService = new CsvService(new ReceitaService());
    CsvProcessor csvProcessor = new CsvProcessor(csvService);
    String VALID_CSV = "src/test/java/resources/validCsv.csv";
    String INVALID_CSV = "src/test/java/resources/invalidCsv.csv";

    @Test
    public void shouldAcceptCsvFile(){
        Boolean result = csvProcessor.accept(VALID_CSV);
        assertEquals(true, result);
    }

    @Test
    public void shouldNotAcceptCsvFile(){
        Boolean result = csvProcessor.accept(INVALID_CSV);
        assertEquals(false, result);
    }

    @Test
    public void shouldProcessCsvFile(){
        List<CsvData> expectedResult = buildACsvList();
        Optional<List<CsvData>> result = csvProcessor.process(VALID_CSV);
        assertEquals(expectedResult, result.get());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotProcessCsvFile(){
        csvProcessor.process(INVALID_CSV);
    }

    private List<CsvData> buildACsvList() {
        List<CsvData> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CsvData("101", "1223", 12.0, "I", "falha na atualizacao"));
        expectedResultList.add(new CsvData("0101", "122256", 100.0, "A", "atualizado"));
        return expectedResultList;
    }
}
