package com.bank.savings.transaction.service;

import com.bank.savings.transaction.vo.*;

public interface SavingsAccountTransactionService {
    public UserTransactionHistoryOutputVO getUserTransactionHistory(UserTransactionHistoryInputVO userTransactionHistoryInputVO);
    public GeneralTransactionVO interestCredit(InterestCreditInputVO interestCreditInputVO);
    public GeneralTransactionVO transactionWithdrawals(TransactionWithdrawalsInputVO transactionWithdrawalsInputVO);
    public GeneralTransactionVO TransactionDeposits(TransactionDepositsInputVO transactionDepositsInputVO);
    public GeneralTransactionVO TransferDeposits(TransferDepositsInputVO transferDepositsInputVO);
    public GeneralTransactionVO TransferDeduction(TransferDeductionInputVO transferDeductionInputVO);
    public GeneralTransactionVO atmWithdrawals(AtmTransactionInputVO atmTransactionInputVO);
}
