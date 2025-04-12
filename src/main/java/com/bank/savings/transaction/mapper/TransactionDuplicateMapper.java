package com.bank.savings.transaction.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class TransactionDuplicateMapper implements RowMapper {
    public Integer mapRow(ResultSet rs, int rowNo) throws SQLException {
        return rs.getInt("transaction_duplicate");
    }
}
