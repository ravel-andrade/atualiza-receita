package com.db.atualizareceita.service;

public interface Receita {

    public boolean updateAccount(String agency, String account, double balance, String status) throws InterruptedException;
}
