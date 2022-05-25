package com.db.atualizareceita.processor;

import com.db.atualizareceita.model.CsvData;
import com.db.atualizareceita.services.AccountService;
import com.db.atualizareceita.services.CsvService;

import java.util.List;
import java.util.Optional;

public class CsvProcessor implements Processor{
    CsvService csvService;
    AccountService accountService;

    public CsvProcessor(CsvService csvService, AccountService accountService) {
        this.csvService = csvService;
        this.accountService = accountService;
    }

    public boolean accept(String csvPath) {
        return csvService.csvFileIsValid(csvPath);
    }

    public Optional<List<CsvData>> process(String csvPath) {
        List<CsvData> csvData = readCsvFile(csvPath);
        if(!csvData.isEmpty()){
            return Optional.of(updateAccounts(csvData));
        }
        return Optional.empty();
    }

    private List<CsvData> readCsvFile(String csvPath){
        return csvService.extractDataFromCsv(csvPath);
    }

    private List<CsvData> updateAccounts(List<CsvData> csvData){
        return accountService.updateAccountsInfo(csvData);
    }
}
