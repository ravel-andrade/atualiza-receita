package com.db.atualizareceita.processor;

import com.db.atualizareceita.model.CsvData;
import com.db.atualizareceita.service.AccountService;
import com.db.atualizareceita.fileMenager.Menager;
import com.db.atualizareceita.validator.Validator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

import static com.db.atualizareceita.Logger.logError;

@Component
public class CsvProcessor implements Processor{
    Validator csvValidator;
    AccountService accountService;

    Menager fileMenager;

    char CSV_SEPARATOR = ';';

    public CsvProcessor(Validator csvValidator, AccountService accountService, Menager fileMenager) {
        this.csvValidator = csvValidator;
        this.accountService = accountService;
        this.fileMenager = fileMenager;
    }

    public boolean accept(String csvPath) {
        try{
            String[] headers = fileMenager.getHeaders(csvPath);
            return csvValidator.csvHeadersAreValid(headers);
        } catch (IOException e) {
            logError("file or directory don't exists");
            return false;
        }
    }

    public Optional<List<CsvData>> process(String csvPath) {
        List<CsvData> csvData = fileMenager.extractDataFromCsv(csvPath);
        if(!csvData.isEmpty()){
            return Optional.of(updateAccounts(csvData));
        }
        return Optional.empty();
    }

    private List<CsvData> updateAccounts(List<CsvData> csvData){
        return accountService.updateAccountsInfo(csvData);
    }

    public String getNewFileName(String destinePath) {
        if(destinePath != null){
            return destinePath + "updated_accounts.csv";
        }
        return "updated_accounts.csv";
    }
}
