package com.bank.savings.transaction.vo;

public class TransactionInfoVO {
    private String transactionTime;
    private String transactionId;
    private String transactionType;
    private double deposits;
    private double transferDeposit;
    private double withdrawals;
    private double transferDeduction;
    private double interest;
    private double cr;
    private double dr;
    private double balance;
    private String atmLocation;

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransferDeposit() {
        return transferDeposit;
    }

    public void setTransferDeposit(double transferDeposit) {
        this.transferDeposit = transferDeposit;
    }

    public double getDeposits() {
        return deposits;
    }

    public void setDeposits(double deposits) {
        this.deposits = deposits;
    }

    public double getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(double withdrawals) {
        this.withdrawals = withdrawals;
    }

    public double getTransferDeduction() {
        return transferDeduction;
    }

    public void setTransferDeduction(double transferDeduction) {
        this.transferDeduction = transferDeduction;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getCr() {
        return cr;
    }

    public void setCr(double cr) {
        this.cr = cr;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getDr() {
        return dr;
    }

    public void setDr(double dr) {
        this.dr = dr;
    }

    public String getAtmLocation() {
        return atmLocation;
    }

    public void setAtmLocation(String atmLocation) {
        this.atmLocation = atmLocation;
    }
}
