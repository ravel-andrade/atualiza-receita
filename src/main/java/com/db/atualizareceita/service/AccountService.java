package com.db.atualizareceita.service;

import com.db.atualizareceita.model.Account;
import com.db.atualizareceita.model.CsvData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final Receita incomeClient;

    public AccountService(Receita incomeClient) {
        this.incomeClient = incomeClient;
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
            return incomeClient.updateAccount(
                    account.getAgency(),
                    account.getAccountNumber(),
                    account.getBalance(),
                    account.getStatus()
            );
        } catch (InterruptedException e) {
            return false;
        }
    }
}
