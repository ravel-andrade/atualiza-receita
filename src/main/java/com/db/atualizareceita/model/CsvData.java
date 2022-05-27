package com.db.atualizareceita.model;

import java.util.Objects;

public class CsvData {
    private Account account;
    private String result;

    public CsvData(Account account, String result) {
        this.account = account;
        this.result = result;
    }

    public CsvData(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CsvData csvData = (CsvData) o;
        return Objects.equals(account, csvData.account) && Objects.equals(result, csvData.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, result);
    }
}
