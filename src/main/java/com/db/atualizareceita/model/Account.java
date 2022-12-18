package com.db.atualizareceita.model;

import java.util.Map;
import java.util.Objects;

public class Account {
    private String agency;
    private String accountNumber;
    private Double balance;
    private String status;

    public Account(String agency, String accountNumber, String balance, String status) {
        try {
            this.balance = Double.parseDouble(balance);
        } catch (Exception e){
            this.balance = null;
        }
        this.accountNumber = accountNumber.replaceAll("\\D+","");
        this.agency = agency;
        this.status = status;
    }

    public static Account buildAccount(Map<String, String> accountData){
        return new Account(
                accountData.get("agencia"),
                accountData.get("conta"),
                accountData.get("saldo"),
                accountData.get("status"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(agency, account.agency) && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(balance, account.balance) && Objects.equals(status, account.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agency, accountNumber, balance, status);
    }

    public String getAgency() {
        return agency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }
}
