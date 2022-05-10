package com.db.atualizareceita.model;

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

}
