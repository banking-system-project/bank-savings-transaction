package com.bank.savings.transaction.mapper;

import com.bank.savings.transaction.dto.GetOfficialDataDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class GetOfficialDataOutputMapper implements RowMapper {

    public GetOfficialDataDTO mapRow(ResultSet rs, int rowNo) throws SQLException {
        GetOfficialDataDTO getOfficialDataOutputDTO = new GetOfficialDataDTO();
        getOfficialDataOutputDTO.setIfscCode(rs.getString("ifsc_code"));
        getOfficialDataOutputDTO.setUserId(rs.getString("user_id"));
        getOfficialDataOutputDTO.setSmId("sm_id");
        getOfficialDataOutputDTO.setPanId("pan_id");

        return getOfficialDataOutputDTO;
    }
}
