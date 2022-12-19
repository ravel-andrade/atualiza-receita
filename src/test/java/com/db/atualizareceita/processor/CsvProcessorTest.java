package com.db.atualizareceita.processor;

import com.db.atualizareceita.model.Account;
import com.db.atualizareceita.model.CsvData;
import com.db.atualizareceita.service.AccountService;
import com.db.atualizareceita.fakeService.ReceitaService;
import com.db.atualizareceita.fileMenager.FileMenager;
import com.db.atualizareceita.service.IncomeClient;
import com.db.atualizareceita.validator.Validator;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@SpringBootTest
public class CsvProcessorTest {
    IncomeClient incomeClient = new IncomeClient(new ReceitaService());

    FileMenager fileMenager = new FileMenager();

    Validator csvValidator = new Validator();
    AccountService accountService = new AccountService(incomeClient);
    CsvProcessor csvProcessor = new CsvProcessor(csvValidator, accountService, fileMenager);
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

    @Test
    public void validateCsvShouldReturnFalseWhenFileDoesntExist(){
        boolean result = csvProcessor.accept(INVALID_CSV);
        assertFalse(result);
    }

    private List<CsvData> buildACsvList() {
        List<CsvData> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CsvData(new Account("101", "1223", "12.0", "I"), "falha na atualizacao"));
        expectedResultList.add(new CsvData(new Account("0101", "122256", "100.0", "A"), "atualizado"));
        return expectedResultList;
    }
}
