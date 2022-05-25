package com.db.atualizareceita.services;

import com.db.atualizareceita.model.Account;
import com.db.atualizareceita.model.CsvData;

import java.util.List;

public class AccountService {

    private ReceitaService receitaService;

    public AccountService(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    public List<CsvData> updateAccountsInfo(List<CsvData> accountsData) {
        for (CsvData accountData : accountsData){
            boolean resultFromUpdate = updateAccount(accountData.getAccount());
            if(resultFromUpdate){
                accountData.setResult("atualizado");
            }else{
                accountData.setResult("falha na atualizacao");
            }
        }
        return accountsData;
    }
    private boolean updateAccount(Account account) {
        try {
            return receitaService.atualizarConta(
                    account.getAgencia(),
                    account.getConta(),
                    account.getSaldo(),
                    account.getStatus()
            );
        } catch (InterruptedException e) {
            return false;
        }
    }
}
