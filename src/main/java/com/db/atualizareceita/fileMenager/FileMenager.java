package com.db.atualizareceita.fileMenager;

import com.db.atualizareceita.model.Account;
import com.db.atualizareceita.model.CsvData;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

import static com.db.atualizareceita.Logger.logError;
@Component
public class FileMenager implements Menager{

    char CSV_SEPARATOR = ';';

    public List<CsvData> extractDataFromCsv(String csvPath) {
        List<CsvData> csvDataList = new ArrayList<>();
        CSVReader fileReader = getFileReader(csvPath);
        List<Map<String, String>> accountsData = getAccountsDataFromCsv(fileReader);
        accountsData.forEach(accountData ->{
            csvDataList.add(new CsvData(Account.buildAccount(accountData)));
        });

        return csvDataList;
    }

    private CSVReader getFileReader(String csvPath) {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build();
        try {
            return new CSVReaderBuilder(
                    new FileReader(csvPath))
                    .withCSVParser(csvParser)
                    .withSkipLines(1)
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<List<String[]>> getDataFromFile(CSVReader fileReader) {
        try {
            return Optional.of(fileReader.readAll());
        } catch (IOException | CsvException e) {
            logError(e.toString());
        }
        return Optional.empty();
    }

    private List<Map<String, String>> getAccountsDataFromCsv(CSVReader fileReader) {
        List<Map<String, String>> accountsData = new ArrayList<>();
        Optional<List<String[]>> accountsDataFromFile = getDataFromFile(fileReader);
        if(accountsDataFromFile.isPresent()){
            accountsData = buildAccountsData(accountsDataFromFile.get());
        }
        return accountsData;
    }

    private List<Map<String, String>> buildAccountsData(List<String[]> accountsDataFromFile) {
        List<Map<String, String>> accountsData = new ArrayList<>();
        accountsDataFromFile.forEach(dataFromFile ->{
            Map<String, String> accountData = new HashMap<>();
            accountData.put("agencia", dataFromFile[0]);
            accountData.put("conta", dataFromFile[1]);
            accountData.put("saldo", dataFromFile[2]);
            accountData.put("status", dataFromFile[3]);
            accountsData.add(accountData);
        });
        return accountsData;
    }

    public boolean saveUpdatedIncomesInCsvFile(List<CsvData> accountsData, String destinePath) {
        String newFileName = getNewFileName(destinePath);
        File newFile = new File(newFileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
            buildHeadersForNewFile(writer);
            writer.newLine();
            accountsData.forEach(csv -> {
                buildCsv(csv, writer);});
        } catch (IOException ex) {
            logError("Couldn't save data in a new file");
            return false;
        }
        System.out.println("Success: Your new file was saved at: "+ newFileName);
        return true;
    }

    private void buildCsv(CsvData accountData, BufferedWriter writer) {
        Account account = accountData.getAccount();
        try {
            writer.append(account.getAgency()).append(CSV_SEPARATOR)
                    .append(account.getAccountNumber()).append(CSV_SEPARATOR)
                    .append(account.getBalance().toString()).append(CSV_SEPARATOR)
                    .append(account.getStatus()).append(CSV_SEPARATOR)
                    .append(accountData.getResult());
            writer.newLine();
        } catch (IOException e) {
            logError("Could not save data for account:"+account.getAccountNumber()+" in a new csv file");
        }
    }

    private void buildHeadersForNewFile(BufferedWriter writer) throws IOException {
        writer.append("agencia").append(";");
        writer.append("conta").append(";");
        writer.append("saldo").append(";");
        writer.append("status").append(";");
        writer.append("result");
    }

    public String[] getHeaders(String csvPath) throws IOException {
        FileReader fileReader = new FileReader(csvPath);
        BufferedReader buffer = new BufferedReader(fileReader);
        return buffer.readLine().split(";");
    }

    private String getNewFileName(String destinePath) {
        if(destinePath != null){
            return destinePath + "updated_accounts.csv";
        }
        return "updated_accounts.csv";
    }

    public Map<String, String> getCsvDataPath(String[] csvMetadata) {
        Map<String, String> csvDataPath = new HashMap<>();
        for (String path : csvMetadata) {
            if(path.contains(".csv")){
                csvDataPath.putIfAbsent("csvpath", path);
            }else{
                csvDataPath.putIfAbsent("destineurl", path);
            }
        }
        return csvDataPath;
    }
}
