package com.db.atualizareceita.services;

import com.db.atualizareceita.model.Account;
import com.db.atualizareceita.model.CsvData;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.*;

public class CsvService {
    char CSV_SEPARATOR = ';';

    public CsvService() {
    }

    public boolean csvFileIsValid(String csvPath) {
        List<String> errors = findErrorsInCsv(csvPath);
        if(!errors.isEmpty()){
            logError(errors);
            return false;
        }
        return true;
    }

    public List<CsvData> extractDataFromCsv(String csvPath) {
        List<CsvData> csvDataList = new ArrayList<>();
            CSVReader fileReader = getFileReader(csvPath);
            List<Map<String, String>> accountsData = getAccountsData(fileReader);
            accountsData.forEach(accountData ->{
                csvDataList.add(buildCsvData(accountData));
            });

        return csvDataList;
    }



    public void saveUpdatedIncomesInCsvFile(List<CsvData> accountsData, String destinePath) {
        String newFileName = getNewFileName(destinePath);
        File newFile = new File(newFileName);
        try {
            BufferedWriter fileWriter = buildFileWriter(newFile);
            buildHeadersForNewFile(fileWriter);
            accountsData.forEach(accountData -> {
                buildCsv(accountData, fileWriter);
            });
        } catch (IOException ex) {
            logError("Couldn't save data in a new file");
        }
        System.out.println("Success: Your new file was saved at: "+ newFileName);
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

    private String[] getHeaders(String csvPath) throws IOException {
        FileReader fileReader = new FileReader(csvPath);
        BufferedReader buffer = new BufferedReader(fileReader);
        return buffer.readLine().split(";");
    }

    private BufferedWriter buildFileWriter(File newFile) throws IOException {
        return new BufferedWriter(new FileWriter(newFile));
    }

    private CsvData buildCsvData(Map<String, String> accountData) {
        Double income;
        try {
            income = Double.parseDouble(accountData.get("saldo"));
        } catch (Exception e){
            income = null;
        }

        CsvData result = new CsvData(
                new Account(accountData.get("agencia"),
                accountData.get("conta").replaceAll("\\D+",""),
                income,
                accountData.get("status")));
        return result;
    }

    private List<Map<String, String>> getAccountsData(CSVReader fileReader) {
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

    private String getNewFileName(String destinePath) {
        if(destinePath != null){
            return destinePath + "updated_accounts.csv";
        }
        return "updated_accounts.csv";
    }

    private void buildHeadersForNewFile(BufferedWriter writer) throws IOException {
        writer.append("agencia").append(";");
        writer.append("conta").append(";");
        writer.append("saldo").append(";");
        writer.append("status").append(";");
        writer.append("result");
        writer.newLine();
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
    private boolean csvHeadersAreValid(String[] headers) {
        if(headers[0].equals("agencia")
                && headers[1].equals("conta")
                && headers[2].equals("saldo")
                && headers[3].equals("status")
        ){return true;}
        return false;
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

    private void logError(List<String> errors) {
        errors.forEach(error -> System.out.println("Error: "+error));
    }

    private void logError(String error) {
        System.out.println("Error: "+error);
    }

    private List<String> findErrorsInCsv(String csvPath) {
        List<String> errors = new ArrayList<>();
        try {
            String[] headers = getHeaders(csvPath);
            if(!csvHeadersAreValid(headers)){
                errors.add("incorrect headers");
            }
        } catch (IOException error) {
            errors.add("file or directory dont exists");
        }
        return errors;
    }

    private Optional<List<String[]>> getDataFromFile(CSVReader fileReader) {
        try {
            return Optional.of(fileReader.readAll());
        } catch (IOException e) {
            logError(e.toString());
        } catch (CsvException e) {
            logError(e.toString());
        }
        return Optional.empty();
    }
}
