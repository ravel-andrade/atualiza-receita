package com.db.atualizareceita.runner;

import com.db.atualizareceita.model.CsvData;
import com.db.atualizareceita.processor.CsvProcessor;
import com.db.atualizareceita.processor.Processor;
import com.db.atualizareceita.services.CsvService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IncomeSynchronizationRunner {
    Processor processor;
    CsvService csvService;

    public IncomeSynchronizationRunner(CsvProcessor csvProcessorService, CsvService csvService) {
        this.processor = csvProcessorService;
        this.csvService = csvService;
    }

    public void updateIncome(String[] csvMetadata) {
        Map<String, String> incomeDataPaths = csvService.getCsvDataPath(csvMetadata);
        if(!incomeDataPaths.isEmpty()){
            if(processor.accept(incomeDataPaths.get("csvpath"))){
                Optional<List<CsvData>> csvData = processor.process(incomeDataPaths.get("csvpath"));
                csvData.ifPresent(data -> csvService.saveUpdatedIncomesInCsvFile(data, incomeDataPaths.get("destineurl")));
            }
        }{
         System.out.println("Error: Missing csv path");
        }
    }
}
