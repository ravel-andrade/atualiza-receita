package com.db.atualizareceita;

import com.db.atualizareceita.controller.IncomeSynchronizationController;
import com.db.atualizareceita.services.CsvProcessor;
import com.db.atualizareceita.services.CsvService;
import com.db.atualizareceita.services.ReceitaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public ReceitaService receitaService(){
        return new ReceitaService();
    }

    @Bean
    public CsvService csvService(ReceitaService receitaService){
        return new CsvService(receitaService);
    }
    @Bean
    public CsvProcessor csvProcessor(CsvService csvService){
        return new CsvProcessor(csvService);
    }
    @Bean
    public IncomeSynchronizationController synchronizationController(CsvProcessor csvProcessor, CsvService csvService){
        return new IncomeSynchronizationController(csvProcessor, csvService);
    }
}
