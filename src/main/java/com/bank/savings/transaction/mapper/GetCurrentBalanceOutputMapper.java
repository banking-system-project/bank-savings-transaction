package com.bank.savings.transaction.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GetCurrentBalanceOutputMapper implements RowMapper {
    public Double mapRow(ResultSet rs, int rowNo) throws SQLException{
        double currentBalance;
        currentBalance = rs.getDouble("latest_balance");
        return currentBalance;
    }
}
