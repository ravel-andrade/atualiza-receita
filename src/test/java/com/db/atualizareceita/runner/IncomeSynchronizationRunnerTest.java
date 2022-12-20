package com.db.atualizareceita.runner;
import com.db.atualizareceita.model.Account;
import com.db.atualizareceita.model.CsvData;
import com.db.atualizareceita.processor.CsvProcessor;
import com.db.atualizareceita.fileMenager.FileMenager;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class IncomeSynchronizationRunnerTest {
    FileMenager menager = mock(FileMenager.class);
    CsvProcessor csvProcessor = mock(CsvProcessor.class);
    IncomeSynchronizationRunner incomeSynchronizationRunner = new IncomeSynchronizationRunner(csvProcessor, menager);
    String VALID_CSV = "src/test/java/resources/validCsv.csv";
    String INVALID_CSV = "src/test/java/resources/invalidCsv.csv";
    @Test
    public void shouldUpdateIncome(){
        String[] metadata = {VALID_CSV};
        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("csvpath", VALID_CSV);

        when(menager.getCsvDataPath(metadata)).thenReturn(metadataMap);
        when(csvProcessor.accept(VALID_CSV)).thenReturn(true);
        when(csvProcessor.process(VALID_CSV)).thenReturn(Optional.of(buildACsvList()));

        incomeSynchronizationRunner.updateIncome(metadata);

        verify(menager, times(1)).getCsvDataPath(metadata);
        verify(csvProcessor, times(1)).accept(VALID_CSV);
        verify(csvProcessor, times(1)).process(VALID_CSV);
        verify(menager, times(1)).saveUpdatedIncomesInCsvFile(buildACsvList(), null);
    }

    @Test
    public void shouldNotUpdateIncomeWhenMissingFile(){
        String[] metadata = {};
        incomeSynchronizationRunner.updateIncome(metadata);

        verify(menager, times(1)).getCsvDataPath(metadata);
        verify(csvProcessor, times(0)).accept(any());
        verify(csvProcessor, times(0)).process(any());
    }

    @Test
    public void shouldNotUpdateIncomeWhenFileIsNotCsv(){
        String[] metadata = {"file", "directory"};
        incomeSynchronizationRunner.updateIncome(metadata);

        verify(menager, times(1)).getCsvDataPath(metadata);
        verify(csvProcessor, times(0)).accept(any());
        verify(csvProcessor, times(0)).process(any());
    }

    @Test
    public void shouldNotUpdateIncomeWhenCsvIsInvalid(){
        String[] metadata = {INVALID_CSV};
        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("csvpath", INVALID_CSV);

        when(menager.getCsvDataPath(metadata)).thenReturn(metadataMap);
        when(csvProcessor.accept(INVALID_CSV)).thenReturn(false);

        incomeSynchronizationRunner.updateIncome(metadata);

        verify(menager, times(1)).getCsvDataPath(metadata);
        verify(csvProcessor, times(1)).accept(INVALID_CSV);
        verify(csvProcessor, times(0)).process(INVALID_CSV);
        verify(menager, times(0)).saveUpdatedIncomesInCsvFile(buildACsvList(), null);
    }

    private List<CsvData> buildACsvList() {
        List<CsvData> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CsvData(new Account("101", "1223", "12.0", "I")));
        expectedResultList.add(new CsvData(new Account("0101", "122256", "100.0", "A")));
        return expectedResultList;
    }

}
