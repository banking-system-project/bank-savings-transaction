package com.bank.savings.transaction.mapper;

import com.bank.savings.transaction.dto.TransactionInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class GetTransactionInfoOutputMapper implements RowMapper {
    private static final Logger log = LoggerFactory.getLogger(GetTransactionInfoOutputMapper.class);

    public TransactionInfoDTO mapRow(ResultSet rs, int rowNo) throws SQLException {

        TransactionInfoDTO transactionInfoOutputDTO = new TransactionInfoDTO();

        transactionInfoOutputDTO.setTransactionId(rs.getString("transaction_id"));
        log.info("adding transaction id: "+transactionInfoOutputDTO.getTransactionId());

        transactionInfoOutputDTO.setTransactionTime(rs.getString("transaction_time"));
        log.info("adding transaction time: "+transactionInfoOutputDTO.getTransactionTime());

        transactionInfoOutputDTO.setTransactionType(rs.getString("transaction_type"));
        log.info("adding transaction type: "+transactionInfoOutputDTO.getTransactionType());

        transactionInfoOutputDTO.setDeposits(rs.getDouble("deposits"));
        log.info("adding deposits: "+transactionInfoOutputDTO.getDeposits());

        transactionInfoOutputDTO.setTransferDeposit(rs.getDouble("transfer_deposit"));
        log.info("adding transfer deposits: "+transactionInfoOutputDTO.getTransferDeposit());

        transactionInfoOutputDTO.setWithdrawals(rs.getDouble("withdrawals"));
        log.info("adding withdrawals: "+transactionInfoOutputDTO.getWithdrawals());

        transactionInfoOutputDTO.setTransferDeduction(rs.getDouble("transfer_deduction"));
        log.info("adding transfer deduction: "+transactionInfoOutputDTO.getTransferDeduction());

        transactionInfoOutputDTO.setInterest(rs.getDouble("interest"));
        log.info("adding interest: "+transactionInfoOutputDTO.getInterest());

        transactionInfoOutputDTO.setCr(rs.getDouble("cr"));
        log.info("adding cr: "+transactionInfoOutputDTO.getCr());

        transactionInfoOutputDTO.setDr(rs.getDouble("dr"));
        log.info("adding dr: "+transactionInfoOutputDTO.getDr());

        transactionInfoOutputDTO.setBalance(rs.getDouble("balance"));
        log.info("adding balance: "+transactionInfoOutputDTO.getBalance());

        transactionInfoOutputDTO.setAtmLocation(rs.getString("atm_location"));
        log.info("adding Atm location: "+transactionInfoOutputDTO.getAtmLocation());

        return transactionInfoOutputDTO;
    }
}
