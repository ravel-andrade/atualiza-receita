package com.db.atualizareceita.model;

import java.util.Objects;

public class CsvData {
    private String agencia;
    private String conta;
    private Double saldo;
    private String status;
    private String result;

    public CsvData(String agencia, String conta, Double saldo, String status) {
        this.agencia = agencia;
        this.conta = conta;
        this.saldo = saldo;
        this.status = status;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getConta() {
        return conta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public String getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CsvData csvData = (CsvData) o;
        return Objects.equals(agencia, csvData.agencia) && Objects.equals(conta, csvData.conta) && Objects.equals(saldo, csvData.saldo) && Objects.equals(status, csvData.status) && Objects.equals(result, csvData.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agencia, conta, saldo, status, result);
    }

}
