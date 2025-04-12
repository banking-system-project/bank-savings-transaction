package com.bank.savings.transaction.vo;

import java.util.List;

public class UserTransactionHistoryOutputVO {
    private GetOfficialDataVO getOfficialDataVO;
    private List<TransactionInfoVO> transactionInfoVOList;

    public GetOfficialDataVO getGetOfficialDataVO() {
        return getOfficialDataVO;
    }

    public void setGetOfficialDataVO(GetOfficialDataVO getOfficialDataVO) {
        this.getOfficialDataVO = getOfficialDataVO;
    }

    public List<TransactionInfoVO> getTransactionInfoVOList() {
        return transactionInfoVOList;
    }

    public void setTransactionInfoVOList(List<TransactionInfoVO> transactionInfoVOList) {
        this.transactionInfoVOList = transactionInfoVOList;
    }
}
