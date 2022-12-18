package com.db.atualizareceita.fileMenager;

import com.db.atualizareceita.model.CsvData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Menager {
    String[] getHeaders(String csvPath) throws IOException;
    public List<CsvData> extractDataFromCsv(String csvPath);
    public boolean saveUpdatedIncomesInCsvFile(List<CsvData> accountsData, String destinePath);
    public Map<String, String> getCsvDataPath(String[] csvMetadata);
}
