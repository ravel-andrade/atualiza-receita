package com.db.atualizareceita;

import com.db.atualizareceita.runner.IncomeSynchronizationRunner;
import com.db.atualizareceita.processor.CsvProcessor;
import com.db.atualizareceita.services.AccountService;
import com.db.atualizareceita.services.CsvService;
import com.db.atualizareceita.fakeService.ReceitaService;
import com.db.atualizareceita.services.IncomeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public ReceitaService receitaService(){
        return new ReceitaService();
    }

    @Bean
    public CsvService csvService(){
        return new CsvService();
    }
    @Bean
    public CsvProcessor csvProcessor(CsvService csvService, AccountService accountService){
        return new CsvProcessor(csvService, accountService);
    }

    @Bean
    public AccountService accountService(IncomeService incomeService){
        return new AccountService(incomeService);
    }

    @Bean
    public IncomeService incomeService(ReceitaService receitaService){
        return new IncomeService(receitaService);
    }

    @Bean
    public IncomeSynchronizationRunner synchronizationController(CsvProcessor csvProcessor, CsvService csvService){
        return new IncomeSynchronizationRunner(csvProcessor, csvService);
    }
}
