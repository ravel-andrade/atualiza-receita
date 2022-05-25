package com.db.atualizareceita.services;

public interface IncomeServiceInterface {

    public boolean updateAccount(String agency, String account, double balance, String status) throws InterruptedException;
}
