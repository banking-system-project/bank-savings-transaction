package com.bank.savings.transaction.dao;

import com.bank.savings.transaction.dto.*;
import com.bank.savings.transaction.mapper.*;
import com.bank.savings.transaction.vo.GeneralTransactionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.bank.savings.transaction.util.SqlQueriesConstants.*;

@Repository
public class SavingsAccountTransactionDAO {

    private static final Logger logger = LoggerFactory.getLogger(SavingsAccountTransactionDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GetCurrentBalanceOutputMapper getCurrentBalanceOutputMapper;

    @Autowired
    private GetOfficialDataOutputMapper getOfficialDataOutputMapper;

    @Autowired
    private GetTransactionInfoOutputMapper getTransactionInfoOutputMapper;

    @Autowired
    private SavingsAccountExistsMapper savingsAccountExistsMapper;

    @Autowired
    private TransactionDuplicateMapper transactionDuplicateMapper;

    public GeneralTransactionVO interestCredit(InterestCreditInputDTO interestCreditInputDTO){
        logger.info("DAO Layer: initializing for interest credit");
        GeneralTransactionVO generalTransactionVO = new GeneralTransactionVO();

        logger.info("DAO Layer: initializing all required variables for interest credit");
        Double currentBalance;
        Double interestRate = 5.0;
        int exists = 0;
        int result = 0;
        try{
            logger.info("DAO layer: checking the account is valid or not before interest credit");
            exists = (Integer) jdbcTemplate.queryForObject(ACCOUNT_EXISTS, savingsAccountExistsMapper, new Object[]{interestCreditInputDTO.getCifId(), interestCreditInputDTO.getAccountNo()});
            if (exists !=1){
                logger.error("DAO Layer: the account is invalid");
                throw new RuntimeException("Account does not exist");
            }else{
               //fetch current balance
                logger.info("DAO Layer: proceed with valid account. fetching current balance");
               currentBalance = (Double) jdbcTemplate.queryForObject(GET_CURRENT_BALANCE, getCurrentBalanceOutputMapper, new Object[]{interestCreditInputDTO.getCifId(), interestCreditInputDTO.getAccountNo()});

               logger.info("DAO layer: calculating the interest");
               Double interest = ((currentBalance * interestRate)/100);
               interestCreditInputDTO.setInterest(interest);
               interestCreditInputDTO.setCr(interest);

               logger.info("DAO layer: calculating the updated balance after adding interest ");
               currentBalance = currentBalance + interest;
               interestCreditInputDTO.setBalance(currentBalance);
                logger.info("entire input dto {}",interestCreditInputDTO.toString());
                if (interestCreditInputDTO.getBalance() == null) {
                    logger.error("Balance is null before DB insert!");
                } else {
                    logger.info("Balance before DB insert: {}", interestCreditInputDTO.getBalance());
                }

               logger.info("DAO layer: updating interest table");
                result = jdbcTemplate.update(INTEREST_CREDIT,   interestCreditInputDTO.getTransactionId(),
                                                                interestCreditInputDTO.getCifId(),
                                                                interestCreditInputDTO.getIfscCode(),
                                                                interestCreditInputDTO.getUserId(),
                                                                interestCreditInputDTO.getSmId(),
                                                                interestCreditInputDTO.getAccountNo(),
                                                                interestCreditInputDTO.getCr(),
                                                                interestCreditInputDTO.getBalance(),
                                                                interestCreditInputDTO.getInterest()
                );

                if(result!=0){
                    logger.info("DAO Layer: Data is added into the interest table");
                    generalTransactionVO.setTransactionStatus("success");
                    generalTransactionVO.setOperationMessage("Interest is credited for the account");
                }else{
                    logger.error("DAO Layer: Issue while inserting data into table");
                    generalTransactionVO.setTransactionStatus("Failed");
                    generalTransactionVO.setOperationMessage("Issue while inserting data into table");
                }
                return  generalTransactionVO;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GeneralTransactionVO TransactionWithdrawals(TransactionWithdrawalsInputDTO transactionWithdrawalsInputDTO){
        logger.info("DAO Layer: initializing for transaction withdrawal");
        GeneralTransactionVO generalTransactionVO = new GeneralTransactionVO();

        logger.info("DAO Layer: initializing all required variables for transaction withdrawal");
        double currentBalance = 0.0;
        int exists = 0;
        int result = 0;
        try{
            logger.info("DAO layer: checking the account is valid or not before transaction withdrawal");
            logger.info("account number: {} cifid: {}",transactionWithdrawalsInputDTO.getAccountNo(), transactionWithdrawalsInputDTO.getCifId());
            exists = (Integer) jdbcTemplate.queryForObject(ACCOUNT_EXISTS, savingsAccountExistsMapper, new Object[]{transactionWithdrawalsInputDTO.getCifId(), transactionWithdrawalsInputDTO.getAccountNo()});
            logger.info("exists value: {}",exists);
            if (exists !=1){
                logger.error("DAO Layer: the account is invalid for transaction withdrawal");
                throw new RuntimeException("Account does not exist");
            }else{
                //fetch current balance
                logger.info("DAO Layer: proceed with valid account. fetching current balance before transaction withdrawal");
                currentBalance = (Double) jdbcTemplate.queryForObject(GET_CURRENT_BALANCE, getCurrentBalanceOutputMapper, new Object[]{transactionWithdrawalsInputDTO.getCifId(), transactionWithdrawalsInputDTO.getAccountNo()});

                if(currentBalance > transactionWithdrawalsInputDTO.getWithdrawals()){
                    logger.info("DAO layer: if current balance is greater than withdrawal amount then going for the transaction");
                    transactionWithdrawalsInputDTO.setDr(transactionWithdrawalsInputDTO.getWithdrawals());

                    logger.info("DAO layer: calculating the updated balance after transaction withdrawal ");
                    currentBalance = currentBalance - transactionWithdrawalsInputDTO.getWithdrawals() ;
                    transactionWithdrawalsInputDTO.setBalance(currentBalance);

                    logger.info("DAO layer: updating transaction withdrawal table");
                    result = jdbcTemplate.update(TRANSACTION_WITHDRAWALS, new Object[]{
                            transactionWithdrawalsInputDTO.getTransactionId(),
                            transactionWithdrawalsInputDTO.getCifId(),
                            transactionWithdrawalsInputDTO.getIfscCode(),
                            transactionWithdrawalsInputDTO.getUserId(),
                            transactionWithdrawalsInputDTO.getSmId(),
                            transactionWithdrawalsInputDTO.getAccountNo(),
                            transactionWithdrawalsInputDTO.getDr(),
                            transactionWithdrawalsInputDTO.getWithdrawals(),
                            transactionWithdrawalsInputDTO.getBalance()
                    });

                    if(result!=0){
                        logger.info("DAO Layer: Data is added into the transaction withdrawal table");
                        generalTransactionVO.setTransactionStatus("success");
                        generalTransactionVO.setOperationMessage("transaction withdrawal is completed for the account");
                    }else{
                        logger.error("DAO Layer: Issue while inserting data into transaction withdrawal table");
                        generalTransactionVO.setTransactionStatus("Failed");
                        generalTransactionVO.setOperationMessage("Issue while inserting data into table");
                    }
                    return  generalTransactionVO;
                }
                else{
                    logger.error("DAO Layer: current balance is less than withdrawal balance");
                    generalTransactionVO.setTransactionStatus("Failed");
                    generalTransactionVO.setOperationMessage("current balance is less than withdrawal balance");
                    return generalTransactionVO;
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GeneralTransactionVO TransactionDeposits(TransactionDepositsInputDTO transactionDepositsInputDTO){
        logger.info("DAO Layer: initializing for transaction deposits");
        GeneralTransactionVO generalTransactionVO = new GeneralTransactionVO();

        logger.info("DAO Layer: initializing all required variables for transaction deposits");
        double currentBalance = 0.0;
        int exists = 0;
        int result = 0;
        try{
            logger.info("DAO layer: checking the account is valid or not before transaction deposits");
            exists = (Integer) jdbcTemplate.queryForObject(ACCOUNT_EXISTS, savingsAccountExistsMapper, new Object[]{transactionDepositsInputDTO.getCifId(), transactionDepositsInputDTO.getAccountNo()});
            if (exists !=1){
                logger.error("DAO Layer: the account is invalid for transaction deposits");
                throw new RuntimeException("Account does not exist");
            }else{
                //fetch current balance
                logger.info("DAO Layer: proceed with valid account. ");

                transactionDepositsInputDTO.setCr(transactionDepositsInputDTO.getDeposits());

                logger.info("DAO layer: calculating the updated balance after transaction deposits ");
                currentBalance = currentBalance + transactionDepositsInputDTO.getDeposits() ;
                transactionDepositsInputDTO.setBalance(currentBalance);

                logger.info("DAO layer: updating transaction deposits table");
                result = jdbcTemplate.update(TRANSACTION_DEPOSITS, new Object[]{
                            transactionDepositsInputDTO.getTransactionId(),
                            transactionDepositsInputDTO.getCifId(),
                            transactionDepositsInputDTO.getIfscCode(),
                            transactionDepositsInputDTO.getUserId(),
                            transactionDepositsInputDTO.getSmId(),
                            transactionDepositsInputDTO.getAccountNo(),
                            transactionDepositsInputDTO.getCr(),
                            transactionDepositsInputDTO.getDeposits(),
                            transactionDepositsInputDTO.getBalance()
                });

                if(result!=0){
                    logger.info("DAO Layer: Data is added into the transaction deposits table");
                    generalTransactionVO.setTransactionStatus("success");
                    generalTransactionVO.setOperationMessage("Transaction deposit is credited for the account");
                }else{
                    logger.error("DAO Layer: Issue while inserting data into transaction deposits table");
                    generalTransactionVO.setTransactionStatus("Failed");
                    generalTransactionVO.setOperationMessage("Issue while inserting data into table");
                }
                return  generalTransactionVO;



            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GeneralTransactionVO TransferDeposits(TransferDepositsInputDTO transferDepositsInputDTO){
        logger.info("DAO Layer: initializing for transfer deposits");
        GeneralTransactionVO generalTransactionVO = new GeneralTransactionVO();

        logger.info("DAO Layer: initializing all required variables for transfer deposits");
        double currentBalance = 0.0;
        int exists = 0;
        int result = 0;
        try{
            logger.info("DAO layer: checking the account is valid or not before transfer deposits");
            exists = (Integer) jdbcTemplate.queryForObject(ACCOUNT_EXISTS, savingsAccountExistsMapper, new Object[]{transferDepositsInputDTO.getCifId(), transferDepositsInputDTO.getAccountNo()});
            if (exists !=1){
                logger.error("DAO Layer: the account is invalid for transfer deposits");
                throw new RuntimeException("Account does not exist");
            }else{
                //fetch current balance
                logger.info("DAO Layer: proceed with valid account for transfer deposits. ");

                transferDepositsInputDTO.setCr(transferDepositsInputDTO.getTransferDeposits());

                logger.info("DAO layer: calculating the updated balance after transfer deposits ");
                currentBalance = currentBalance + transferDepositsInputDTO.getTransferDeposits() ;
                transferDepositsInputDTO.setBalance(currentBalance);

                logger.info("DAO layer: updating transfer deposits table");
                result = jdbcTemplate.update(TRANSFER_DEPOSITS, new Object[]{
                        transferDepositsInputDTO.getTransactionId(),
                        transferDepositsInputDTO.getCifId(),
                        transferDepositsInputDTO.getIfscCode(),
                        transferDepositsInputDTO.getUserId(),
                        transferDepositsInputDTO.getSmId(),
                        transferDepositsInputDTO.getAccountNo(),
                        transferDepositsInputDTO.getCr(),
                        transferDepositsInputDTO.getTransferDeposits(),
                        transferDepositsInputDTO.getBalance()
                });

                if(result!=0){
                    logger.info("DAO Layer: Data is added into the transfer deposits table");
                    generalTransactionVO.setTransactionStatus("success");
                    generalTransactionVO.setOperationMessage("Transfer deposit is credited for the account");
                }else{
                    logger.error("DAO Layer: Issue while inserting data into transfer deposits table");
                    generalTransactionVO.setTransactionStatus("Failed");
                    generalTransactionVO.setOperationMessage("Issue while inserting data into table");
                }
                return  generalTransactionVO;



            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GeneralTransactionVO TransferDeduction(TransferDeductionInputDTO transferDeductionInputDTO) {
        logger.info("DAO Layer: initializing for transfer deduction");
        GeneralTransactionVO generalTransactionVO = new GeneralTransactionVO();

        logger.info("DAO Layer: initializing all required variables for transfer deduction");
        double currentBalance = 0.0;
        int exists = 0;
        int result = 0;
        try {
            logger.info("DAO layer: checking the account is valid or not before transfer deduction");
            exists = (Integer) jdbcTemplate.queryForObject(ACCOUNT_EXISTS, savingsAccountExistsMapper, new Object[]{transferDeductionInputDTO.getCifId(), transferDeductionInputDTO.getAccountNo()});
            if (exists != 1) {
                logger.error("DAO Layer: the account is invalid for transfer deduction");
                throw new RuntimeException("Account does not exist");
            } else {
                //fetch current balance
                logger.info("DAO Layer: proceed with valid account. fetching current balance before transfer deduction");
                currentBalance = (Double) jdbcTemplate.queryForObject(GET_CURRENT_BALANCE, getCurrentBalanceOutputMapper, new Object[]{transferDeductionInputDTO.getCifId(), transferDeductionInputDTO.getAccountNo()});

                if (currentBalance > transferDeductionInputDTO.getTransferDeduction()) {
                    logger.info("DAO layer: if current balance is greater than withdrawal amount then going for the transfer deduction");
                    transferDeductionInputDTO.setDr(transferDeductionInputDTO.getTransferDeduction());

                    logger.info("DAO layer: calculating the updated balance after transfer deduction");
                    currentBalance = currentBalance - transferDeductionInputDTO.getTransferDeduction();
                    transferDeductionInputDTO.setBalance(currentBalance);

                    logger.info("DAO layer: updating transfer deduction table");
                    result = jdbcTemplate.update(TRANSFER_DEDUCTION, new Object[]{
                            transferDeductionInputDTO.getTransactionId(),
                            transferDeductionInputDTO.getCifId(),
                            transferDeductionInputDTO.getIfscCode(),
                            transferDeductionInputDTO.getUserId(),
                            transferDeductionInputDTO.getSmId(),
                            transferDeductionInputDTO.getAccountNo(),
                            transferDeductionInputDTO.getDr(),
                            transferDeductionInputDTO.getTransferDeduction(),
                            transferDeductionInputDTO.getBalance()
                    });

                    if (result != 0) {
                        logger.info("DAO Layer: Data is added into the transfer deduction table");
                        generalTransactionVO.setTransactionStatus("success");
                        generalTransactionVO.setOperationMessage(" transfer deduction is completed for the account");
                    } else {
                        logger.error("DAO Layer: Issue while inserting data into transfer deduction table");
                        generalTransactionVO.setTransactionStatus("Failed");
                        generalTransactionVO.setOperationMessage("Issue while inserting data into table");
                    }
                    return generalTransactionVO;
                } else {
                    logger.error("DAO Layer: current balance is less than transfer deduction balance");
                    generalTransactionVO.setTransactionStatus("Failed");
                    generalTransactionVO.setOperationMessage("current balance is less than withdrawal balance");
                    return generalTransactionVO;
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GeneralTransactionVO atmWithdrawals(ATMTransactionInputDTO atmTransactionInputDTO) {
        logger.info("DAO Layer: initializing for atm withdrawals");
        GeneralTransactionVO generalTransactionVO = new GeneralTransactionVO();

        logger.info("DAO Layer: initializing all required variables for atm withdrawals");
        double currentBalance = 0.0;
        int exists = 0;
        int result = 0;
        try {
            logger.info("DAO layer: checking the account is valid or not before atm withdrawals");
            exists = (Integer) jdbcTemplate.queryForObject(ACCOUNT_EXISTS, savingsAccountExistsMapper, new Object[]{atmTransactionInputDTO.getCifId(), atmTransactionInputDTO.getAccountNo()});
            if (exists != 1) {
                logger.error("DAO Layer: the account is invalid for atm withdrawals");
                throw new RuntimeException("Account does not exist");
            } else {
                //fetch current balance
                logger.info("DAO Layer: proceed with valid account. fetching current balance before atm withdrawals");
                currentBalance = (Double) jdbcTemplate.queryForObject(GET_CURRENT_BALANCE, getCurrentBalanceOutputMapper, new Object[]{atmTransactionInputDTO.getCifId(), atmTransactionInputDTO.getAccountNo()});

                if (currentBalance > atmTransactionInputDTO.getWithdrawals()) {
                    logger.info("DAO layer: if current balance is greater than withdrawal amount then going for the atm withdrawals");
                    atmTransactionInputDTO.setDr(atmTransactionInputDTO.getWithdrawals());

                    logger.info("DAO layer: calculating the updated balance after atm withdrawals");
                    currentBalance = currentBalance - atmTransactionInputDTO.getWithdrawals();
                    atmTransactionInputDTO.setBalance(currentBalance);

                    logger.info("DAO layer: updating atm withdrawals table");
                    result = jdbcTemplate.update(ATM_TRANSACTION,
                            atmTransactionInputDTO.getTransactionId(),
                            atmTransactionInputDTO.getCifId(),
                            atmTransactionInputDTO.getIfscCode(),
                            atmTransactionInputDTO.getUserId(),
                            atmTransactionInputDTO.getSmId(),
                            atmTransactionInputDTO.getAccountNo(),
                            atmTransactionInputDTO.getDr(),
                            atmTransactionInputDTO.getWithdrawals(),
                            atmTransactionInputDTO.getBalance(),
                            atmTransactionInputDTO.getAtmLocation()
                    );

                    if (result != 0) {
                        logger.info("DAO Layer: Data is added into the atm withdrawals table");
                        generalTransactionVO.setTransactionStatus("success");
                        generalTransactionVO.setOperationMessage(" transfer deduction is completed for the account");
                    } else {
                        logger.error("DAO Layer: Issue while inserting data into atm withdrawals table");
                        generalTransactionVO.setTransactionStatus("Failed");
                        generalTransactionVO.setOperationMessage("Issue while inserting data into table");
                    }
                    return generalTransactionVO;
                } else {
                    logger.error("DAO Layer: current balance is less than atm withdrawals balance");
                    generalTransactionVO.setTransactionStatus("Failed");
                    generalTransactionVO.setOperationMessage("current balance is less than withdrawal balance");
                    return generalTransactionVO;
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String generateTransactionId(){

        logger.info("DAO layer: initializing variables for generating transaction id");

        Random rnd = new Random();
        String transactionId;
        boolean isIdPresent;
        
        try{
            do{
                logger.info("DAO Layer: creating new transaction id before verification");
                int n = 100000 + rnd.nextInt(900000);
                transactionId = "TR"+n;

                logger.info("DAO layer: Check in the DB whether the generated transaction id is already available or not");
                int count = (Integer)  jdbcTemplate.queryForObject(TRANSACTION_DUPLICATE, transactionDuplicateMapper, new Object[]{transactionId});
                isIdPresent = count > 0;

            }while(isIdPresent);
            logger.info("DAO layer: new transasction id --> {} is generated", transactionId);
            return transactionId;
        } catch (Exception e) {
            logger.error("DAO Layer: getting error while creating transaction id: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public UserTransactionHistoryOutputDTO getUserTransactionHistory(UserTransactionHistoryInputDTO userTransactionHistoryInputDTO){
        logger.info("DAO layer: constants updated for getting user details extraction");
        UserTransactionHistoryOutputDTO userTransactionHistoryOutputDTO = new UserTransactionHistoryOutputDTO();
        GetOfficialDataDTO getOfficialDataDTO = new GetOfficialDataDTO();
        List<TransactionInfoDTO> transactionInfoDTOList = new ArrayList<>();


        try{
            logger.info("DAO Layer: fetching user official data");
            getOfficialDataDTO = (GetOfficialDataDTO) jdbcTemplate.queryForObject(GET_OFFICIAL_DATA, getOfficialDataOutputMapper, new Object[]{userTransactionHistoryInputDTO.getCifId(), userTransactionHistoryInputDTO.getAccountId()});

            logger.info("DAO Layer: fetching transaction history for the user");
            transactionInfoDTOList = (List<TransactionInfoDTO>) jdbcTemplate.query(GET_TRANSACTION_INFO, getTransactionInfoOutputMapper, new Object[]{userTransactionHistoryInputDTO.getCifId(), userTransactionHistoryInputDTO.getAccountId()});

            userTransactionHistoryOutputDTO.setGetOfficialDataDTO(getOfficialDataDTO);
            userTransactionHistoryOutputDTO.setTransactionInfoDTOList(transactionInfoDTOList);

        } catch (RuntimeException e) {
            logger.error("DAO Layer: Issue while fetching info from ");
            throw new RuntimeException(e);
        }
        logger.info("DAO Layer: user transaction information fetched");
        return userTransactionHistoryOutputDTO;
    }
}
