package com.db.atualizareceita.tests;

import com.db.atualizareceita.model.CsvData;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.*;

public class CsvService {
    ReceitaService receitaService;
    char CSV_SEPARATOR = ';';

    public CsvService(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    public boolean csvFileIsValid(String csvPath) {
        try {
            FileReader fileReader = new FileReader(csvPath);
            CSVReader reader = new CSVReader(fileReader);
            BufferedReader buffer = new BufferedReader(fileReader);
            String[] headers = buffer.readLine().split(";");
            if(!csvHeadersAreValid(headers)){
              throw new InvalidParameterException();
            }
        } catch (IOException error) {
            System.out.println("-----Error: this file or directory dont exists");
           return false;
        }catch (InvalidParameterException error){
            System.out.println("-----Error: incorrect headers");
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

    private CsvData buildCsvData(Map<String, String> accountData) {
        Double income;
        try {
            income = Double.parseDouble(accountData.get("saldo"));
        } catch (Exception e){
            income = null;
        }

        return new CsvData(accountData.get("agencia"),
                           accountData.get("conta").replaceAll("\\D+",""),
                           income,
                           accountData.get("status"));
    }

    private List<Map<String, String>> getAccountsData(CSVReader fileReader) {
        List<Map<String, String>> accountsData = new ArrayList<>();
        try {
            List<String[]> accountsDataFromFile = fileReader.readAll();

            accountsDataFromFile.forEach(dataFromFile ->{
                String[] accountFromFile = dataFromFile[0].split(";");
                Map<String, String> accountData = new HashMap<>();
                accountData.put("agencia", accountFromFile[0]);
                accountData.put("conta", accountFromFile[1]);
                accountData.put("saldo", accountFromFile[2]);
                accountData.put("status", accountFromFile[3]);
                accountsData.add(accountData);
            });
        } catch (IOException | CsvException e) {
            System.out.println("-----Error: Cannot read given csv file");
            throw new RuntimeException(e);
        }
        return accountsData;
    }

    public List<CsvData> updateAccountsInfo(List<CsvData> accountsData) {
        for (CsvData accountData : accountsData){
            boolean resultFromUpdate = updateAccount(accountData);
            if(resultFromUpdate){
                accountData.setResult("atualizado");
            }else{
                accountData.setResult("falha na atualizacao");
            }
        }
        return accountsData;
    }

    public void saveUpdatedIncomes(List<CsvData> accountsData, String destinePath) {
        String newFileName;
        if(destinePath != null){
            newFileName = destinePath + "updated_accounts.csv";
        }else{
            newFileName = "updated_accounts.csv";
        }
        File newFile = new File(newFileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
            buildHeaders(writer);
            writer.newLine();
            accountsData.forEach(csv -> {
                buildCsv(csv, writer);
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("-----Success: Your new file was saved at: "+ newFileName);
    }

    private void buildHeaders(BufferedWriter writer) throws IOException {
        writer.append("agencia").append(";");
        writer.append("conta").append(";");
        writer.append("saldo").append(";");
        writer.append("status").append(";");
        writer.append("result");

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

    private CSVReader getFileReader(String csvPath) {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
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

    private void buildCsv(CsvData csv, BufferedWriter writer) {
        try {
            writer.append(csv.getAgencia()).append(CSV_SEPARATOR)
                    .append(csv.getConta()).append(CSV_SEPARATOR)
                    .append(csv.getSaldo().toString()).append(CSV_SEPARATOR)
                    .append(csv.getStatus()).append(CSV_SEPARATOR)
                    .append(csv.getResult());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("-----Error: Could not save data in a new csv file");
            throw new RuntimeException(e);
        }
    }

    private boolean updateAccount(CsvData accountData) {
        try {
            return receitaService.atualizarConta(
                    accountData.getAgencia(),
                    accountData.getConta(),
                    accountData.getSaldo(),
                    accountData.getStatus()
            );
        } catch (InterruptedException e) {
            return false;
        }
    }
}
