package com.db.atualizareceita.runner;

import com.db.atualizareceita.model.CsvData;
import com.db.atualizareceita.processor.CsvProcessor;
import com.db.atualizareceita.processor.Processor;
import com.db.atualizareceita.fileMenager.Menager;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class IncomeSynchronizationRunner {
    Processor processor;
    Menager menager;

    public IncomeSynchronizationRunner(CsvProcessor csvProcessorService, Menager menager) {
        this.processor = csvProcessorService;
        this.menager = menager;
    }

    public void updateIncome(String[] csvMetadata) {
        Map<String, String> incomeDataPaths = menager.getCsvDataPath(csvMetadata);
        if(!incomeDataPaths.isEmpty() && incomeDataPaths.get("csvpath") != null){
            if(processor.accept(incomeDataPaths.get("csvpath"))){
                Optional<List<CsvData>> csvData = processor.process(incomeDataPaths.get("csvpath"));
                csvData.ifPresent(data -> menager.saveUpdatedIncomesInCsvFile(data, incomeDataPaths.get("destineurl")));
            }
        }else{
         System.out.println("Error: Missing csv path");
        }
    }
}
