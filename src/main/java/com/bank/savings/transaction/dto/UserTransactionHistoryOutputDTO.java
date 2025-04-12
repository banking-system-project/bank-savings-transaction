package com.bank.savings.transaction.dto;

import java.util.List;

public class UserTransactionHistoryOutputDTO {
    private GetOfficialDataDTO getOfficialDataDTO;
    private List<TransactionInfoDTO> transactionInfoDTOList;

    public GetOfficialDataDTO getGetOfficialDataDTO() {
        return getOfficialDataDTO;
    }

    public void setGetOfficialDataDTO(GetOfficialDataDTO getOfficialDataDTO) {
        this.getOfficialDataDTO = getOfficialDataDTO;
    }

    public List<TransactionInfoDTO> getTransactionInfoDTOList() {
        return transactionInfoDTOList;
    }

    public void setTransactionInfoDTOList(List<TransactionInfoDTO> transactionInfoDTOList) {
        this.transactionInfoDTOList = transactionInfoDTOList;
    }
}
