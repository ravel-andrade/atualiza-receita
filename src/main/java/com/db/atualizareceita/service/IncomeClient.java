package com.db.atualizareceita.service;

import com.db.atualizareceita.fakeService.ReceitaService;
import org.springframework.stereotype.Component;

@Component
public class IncomeClient implements Receita {
    ReceitaService receitaService;

    public IncomeClient(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @Override
    public boolean updateAccount(String agency, String account, double balance, String status) throws InterruptedException {
        try{
            return receitaService.atualizarConta(agency, account, balance, status);
        }catch (RuntimeException e){
            return updateAccount(agency, account, balance, status);
        }
    }
}
