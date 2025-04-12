package com.bank.savings.transaction.controller;

import com.bank.savings.transaction.service.SavingsAccountTransactionServiceImpl;
import com.bank.savings.transaction.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank/transaction")
public class SavingsAccountTransactionController {

    @Autowired
    private SavingsAccountTransactionServiceImpl savingsAccountTransactionService;

    @PostMapping("/getUserTransactionHistory")
    public ResponseEntity<UserTransactionHistoryOutputVO> getUserTransactionHistory(@RequestBody UserTransactionHistoryInputVO userTransactionHistoryInputDTO){
        UserTransactionHistoryOutputVO userTransactionHistoryOutputVO = savingsAccountTransactionService.getUserTransactionHistory(userTransactionHistoryInputDTO);
        return new ResponseEntity<>(userTransactionHistoryOutputVO, HttpStatus.OK);
    }

    @PostMapping("/interestCredit")
    public ResponseEntity<GeneralTransactionVO> interestCredit(@RequestBody InterestCreditInputVO interestCreditInputVO){
        GeneralTransactionVO generalTransactionVO = savingsAccountTransactionService.interestCredit(interestCreditInputVO);
        return new ResponseEntity<>(generalTransactionVO, HttpStatus.OK);
    }

    @PostMapping("/transactionWithdrawals")
    public ResponseEntity<GeneralTransactionVO> transactionWithdrawals(@RequestBody TransactionWithdrawalsInputVO transactionWithdrawalsInputVO){
        GeneralTransactionVO generalTransactionVO = savingsAccountTransactionService.transactionWithdrawals(transactionWithdrawalsInputVO);
        return new ResponseEntity<>(generalTransactionVO,HttpStatus.OK);
    }

    @PostMapping("/transactionDeposits")
    public ResponseEntity<GeneralTransactionVO> transactionDeposits( @RequestBody TransactionDepositsInputVO transactionDepositsInputVO){
        GeneralTransactionVO generalTransactionVO = savingsAccountTransactionService.TransactionDeposits(transactionDepositsInputVO);
        return new ResponseEntity<>(generalTransactionVO,HttpStatus.OK);
    }

    @PostMapping("/transferDeposits")
    public ResponseEntity<GeneralTransactionVO> transferDeposits( @RequestBody TransferDepositsInputVO transferDepositsInputVO){
        GeneralTransactionVO generalTransactionVO = savingsAccountTransactionService.TransferDeposits(transferDepositsInputVO);
        return new ResponseEntity<>(generalTransactionVO,HttpStatus.OK);
    }

    @PostMapping("/transferDeduction")
    public ResponseEntity<GeneralTransactionVO>  transferDeduction( @RequestBody TransferDeductionInputVO transferDeductionInputVO){
        GeneralTransactionVO generalTransactionVO = savingsAccountTransactionService.TransferDeduction(transferDeductionInputVO);
        return new ResponseEntity<>(generalTransactionVO,HttpStatus.OK);
    }

    @PostMapping("/atmWithdrawals")
    public ResponseEntity<GeneralTransactionVO>  atmWithdrawals( @RequestBody AtmTransactionInputVO atmTransactionInputVO){
        GeneralTransactionVO generalTransactionVO = savingsAccountTransactionService.atmWithdrawals(atmTransactionInputVO);
        return new ResponseEntity<>(generalTransactionVO,HttpStatus.OK);
    }
}
