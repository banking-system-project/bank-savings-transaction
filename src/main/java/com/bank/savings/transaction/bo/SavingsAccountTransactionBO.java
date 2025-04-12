package com.bank.savings.transaction.bo;

import com.bank.savings.transaction.dao.SavingsAccountTransactionDAO;
import com.bank.savings.transaction.dto.*;
import com.bank.savings.transaction.vo.GeneralTransactionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SavingsAccountTransactionBO {

    @Autowired
    private SavingsAccountTransactionDAO savingsAccountTransactionDAO;

    public UserTransactionHistoryOutputDTO getUserTransactionHistory(UserTransactionHistoryInputDTO userTransactionHistoryInputDTO) {
        return savingsAccountTransactionDAO.getUserTransactionHistory(userTransactionHistoryInputDTO);
    }

    public GeneralTransactionVO interestCredit(InterestCreditInputDTO interestCreditInputDTO){
        String transactionId = savingsAccountTransactionDAO.generateTransactionId();
        interestCreditInputDTO.setTransactionId(transactionId);
        return savingsAccountTransactionDAO.interestCredit(interestCreditInputDTO);
    }

    public GeneralTransactionVO transactionWithdrawals(TransactionWithdrawalsInputDTO transactionWithdrawalsInputDTO){
        String transactionId = savingsAccountTransactionDAO.generateTransactionId();
        transactionWithdrawalsInputDTO.setTransactionId(transactionId);
        return savingsAccountTransactionDAO.TransactionWithdrawals(transactionWithdrawalsInputDTO);
    }

    public GeneralTransactionVO TransactionDeposits(TransactionDepositsInputDTO transactionDepositsInputDTO){
        String transactionId = savingsAccountTransactionDAO.generateTransactionId();
        transactionDepositsInputDTO.setTransactionId(transactionId);
        return savingsAccountTransactionDAO.TransactionDeposits(transactionDepositsInputDTO);
    }

    public GeneralTransactionVO TransferDeposits(TransferDepositsInputDTO transferDepositsInputDTO){
        String transactionId = savingsAccountTransactionDAO.generateTransactionId();
        transferDepositsInputDTO.setTransactionId(transactionId);
        return savingsAccountTransactionDAO.TransferDeposits(transferDepositsInputDTO);
    }

    public GeneralTransactionVO TransferDeduction(TransferDeductionInputDTO transferDeductionInputDTO){
        String transactionId = savingsAccountTransactionDAO.generateTransactionId();
        transferDeductionInputDTO.setTransactionId(transactionId);
        return savingsAccountTransactionDAO.TransferDeduction(transferDeductionInputDTO);
    }

    public GeneralTransactionVO atmWithdrawals(ATMTransactionInputDTO atmTransactionInputDTO) {
        String transactionId = savingsAccountTransactionDAO.generateTransactionId();
        atmTransactionInputDTO.setTransactionId(transactionId);
        return savingsAccountTransactionDAO.atmWithdrawals(atmTransactionInputDTO);
    }
}
