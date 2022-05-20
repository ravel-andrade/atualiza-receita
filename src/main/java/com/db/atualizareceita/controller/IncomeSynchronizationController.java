package com.db.atualizareceita.controller;

import com.db.atualizareceita.model.CsvData;
import com.db.atualizareceita.tests.CsvProcessor;
import com.db.atualizareceita.tests.CsvService;

import java.util.List;
import java.util.Map;

public class IncomeSynchronizationController {
    CsvProcessor csvProcessor;
    CsvService csvService;

    public IncomeSynchronizationController(CsvProcessor csvProcessorService, CsvService csvService) {
        this.csvProcessor = csvProcessorService;
        this.csvService = csvService;
    }

    public void updateIncome(String[] csvMetadata) {
        Map<String, String> incomeDataPaths = csvService.getCsvDataPath(csvMetadata);
        if(csvProcessor.accept(incomeDataPaths.get("csvpath"))){
            List<CsvData> csvData = csvProcessor.process(incomeDataPaths.get("csvpath"));
            csvService.saveUpdatedIncomes(csvData, incomeDataPaths.get("destineurl"));
        }

    }
}
