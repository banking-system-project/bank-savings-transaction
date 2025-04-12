package com.bank.savings.transaction.vo;

public class UserTransactionHistoryInputVO {
    private String accountId;
    private String cifId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCifId() {
        return cifId;
    }

    public void setCifId(String cifId) {
        this.cifId = cifId;
    }
}
