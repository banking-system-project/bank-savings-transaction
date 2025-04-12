package com.bank.savings.transaction.service;

import com.bank.savings.transaction.bo.SavingsAccountTransactionBO;
import com.bank.savings.transaction.dto.InterestCreditInputDTO;
import com.bank.savings.transaction.dto.UserTransactionHistoryOutputDTO;
import com.bank.savings.transaction.util.MapperUtil;
import com.bank.savings.transaction.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SavingsAccountTransactionServiceImpl implements SavingsAccountTransactionService{

    @Autowired
    private SavingsAccountTransactionBO savingsAccountTransactionBO;

    @Autowired
    private MapperUtil mapperUtil;

    public UserTransactionHistoryOutputVO getUserTransactionHistory(UserTransactionHistoryInputVO userTransactionHistoryInputVO) {
        UserTransactionHistoryOutputDTO userTransactionHistoryOutputDTO;
        userTransactionHistoryOutputDTO = savingsAccountTransactionBO.getUserTransactionHistory(mapperUtil.userTransactionHistoryInputVOtoDTO(userTransactionHistoryInputVO));
        return mapperUtil.userTransactionHistoryOutputDTOtoVO(userTransactionHistoryOutputDTO);
    }

    public GeneralTransactionVO interestCredit(InterestCreditInputVO interestCreditInputVO){
        return savingsAccountTransactionBO.interestCredit(mapperUtil.interestCreditInputVOtoDTO(interestCreditInputVO));
    }

    public GeneralTransactionVO transactionWithdrawals(TransactionWithdrawalsInputVO transactionWithdrawalsInputVO){
        return savingsAccountTransactionBO.transactionWithdrawals(mapperUtil.transactionWithdrawalsInputVOtoDTO(transactionWithdrawalsInputVO));
    }

    public GeneralTransactionVO TransactionDeposits(TransactionDepositsInputVO transactionDepositsInputVO){
        return  savingsAccountTransactionBO.TransactionDeposits(mapperUtil.transactionDepositsInputVOtoDTO(transactionDepositsInputVO));
    }

    public GeneralTransactionVO TransferDeposits(TransferDepositsInputVO transferDepositsInputVO){
        return savingsAccountTransactionBO.TransferDeposits(mapperUtil.transferDepositsInputVOtoDTO(transferDepositsInputVO));
    }

    public GeneralTransactionVO TransferDeduction(TransferDeductionInputVO transferDeductionInputVO){
        return savingsAccountTransactionBO.TransferDeduction(mapperUtil.transferDeductionInputVOtoDTO(transferDeductionInputVO));
    }

    public GeneralTransactionVO atmWithdrawals(AtmTransactionInputVO atmTransactionInputVO){
        return savingsAccountTransactionBO.atmWithdrawals(mapperUtil.atmTransactionVOtoDTO(atmTransactionInputVO));
    }
}
