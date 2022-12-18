package com.db.atualizareceita.service;
import com.db.atualizareceita.fakeService.ReceitaService;
import com.db.atualizareceita.model.Account;
import com.db.atualizareceita.model.CsvData;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class AccountServiceTest {
    AccountService accountService = new AccountService(new IncomeClient(new ReceitaService()));
    String VALID_CSV = "src/test/java/resources/validCsv.csv";
    String INVALID_CSV = "src/test/java/resources/invalidCsv.csv";
    @Test
    public void shouldUpdateAccountsInfoCorrectly(){
        List<CsvData> expectedResult = buildExpectedACsvList();
        List<CsvData> result = accountService.updateAccountsInfo(buildACsvList());
        assertEquals(expectedResult, result);
    }

    private List<CsvData> buildACsvList() {
        List<CsvData> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CsvData(new Account("101", "1223", "12.0", "I")));
        expectedResultList.add(new CsvData(new Account("0101", "122256", "100.0", "A")));
        return expectedResultList;
    }

    private List<CsvData> buildExpectedACsvList() {
        List<CsvData> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CsvData(new Account("101", "1223", "12.0", "I"), "falha na atualizacao"));
        expectedResultList.add(new CsvData(new Account("0101", "122256", "100.0", "A"), "atualizado"));
        return expectedResultList;
    }


}
