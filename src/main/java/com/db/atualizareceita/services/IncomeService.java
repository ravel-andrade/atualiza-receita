package com.db.atualizareceita.services;

import com.db.atualizareceita.fakeService.ReceitaService;

public class IncomeService implements IncomeServiceInterface {
    ReceitaService receitaService;

    public IncomeService(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @Override
    public boolean updateAccount(String agency, String account, double balance, String status) throws InterruptedException {
        return receitaService.atualizarConta(agency, account, balance, status);
    }
}
