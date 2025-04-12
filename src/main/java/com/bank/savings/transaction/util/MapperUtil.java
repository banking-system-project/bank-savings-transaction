package com.bank.savings.transaction.util;

import com.bank.savings.transaction.dto.*;
import com.bank.savings.transaction.vo.*;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MapperUtil {

    public GetOfficialDataDTO getOfficialDataVOtoDTO(GetOfficialDataVO getOfficialDataVO);
    public GetOfficialDataVO getOfficialDataDTOtoVO(GetOfficialDataDTO getOfficialDataDTO);
    public ATMTransactionInputDTO atmTransactionVOtoDTO(AtmTransactionInputVO atmTransactionInputVO);
    public AtmTransactionInputVO atmTransactionDTOtoVO(ATMTransactionInputDTO atmTransactionInputVO);
    public InterestCreditInputDTO interestCreditInputVOtoDTO(InterestCreditInputVO interestCreditInputVO);
    public InterestCreditInputVO interestCreditInputDTOtoVO(InterestCreditInputDTO interestCreditInputDTO);
    public TransactionDepositsInputDTO transactionDepositsInputVOtoDTO(TransactionDepositsInputVO transactionDepositsInputVO);
    public TransactionDepositsInputVO transactionDepositsInputDTOtoVO(TransactionDepositsInputDTO transactionDepositsInputDTO);
    public TransactionWithdrawalsInputDTO transactionWithdrawalsInputVOtoDTO(TransactionWithdrawalsInputVO transactionWithdrawalsInputVO);
    public TransactionWithdrawalsInputVO transactionWithdrawalsInputDTOtoVO(TransactionWithdrawalsInputDTO transactionWithdrawalsInputDTO);
    public TransferDeductionInputDTO transferDeductionInputVOtoDTO(TransferDeductionInputVO transferDeductionInputVO);
    public TransferDeductionInputVO transferDeductionInputDTOtoVO(TransferDeductionInputDTO transferDeductionInputDTO);
    public TransferDepositsInputDTO transferDepositsInputVOtoDTO (TransferDepositsInputVO transferDepositsInputVO);
    public TransferDepositsInputVO transferDepositsInputDTOtoVO (TransferDepositsInputDTO transferDepositsInputDTO);
    public UserTransactionHistoryInputDTO userTransactionHistoryInputVOtoDTO(UserTransactionHistoryInputVO userTransactionHistoryInputVO);
    public UserTransactionHistoryInputVO userTransactionHistoryInputDTOtoVO(UserTransactionHistoryInputDTO userTransactionHistoryInputDTO);

    default List<TransactionInfoDTO> transactionInfoListVOtoDTO (List<TransactionInfoVO> transactionInfoVOList){
        List<TransactionInfoDTO> transactionInfoDTOList = new ArrayList<TransactionInfoDTO>();
        for(TransactionInfoVO transactionInfoVO: transactionInfoVOList){
            TransactionInfoDTO transactionInfoDTO = new TransactionInfoDTO();


            transactionInfoDTO.setTransactionId(transactionInfoVO.getTransactionId());
            transactionInfoDTO.setTransactionTime(transactionInfoVO.getTransactionTime());
            transactionInfoDTO.setTransactionType(transactionInfoVO.getTransactionType());
            transactionInfoDTO.setCr(transactionInfoVO.getCr());
            transactionInfoDTO.setDr(transactionInfoVO.getDr());
            transactionInfoDTO.setBalance(transactionInfoVO.getBalance());
            transactionInfoDTO.setDeposits(transactionInfoVO.getDeposits());
            transactionInfoDTO.setWithdrawals(transactionInfoVO.getWithdrawals());
            transactionInfoDTO.setTransferDeduction(transactionInfoVO.getTransferDeduction());
            transactionInfoDTO.setTransferDeposit(transactionInfoVO.getTransferDeposit());
            transactionInfoDTO.setInterest(transactionInfoVO.getInterest());
            transactionInfoDTO.setAtmLocation(transactionInfoVO.getAtmLocation());

            transactionInfoDTOList.add(transactionInfoDTO);
        }
        return transactionInfoDTOList;
    }

    default List<TransactionInfoVO> transactionInfoListDTOtoVO (List<TransactionInfoDTO> transactionInfoDTOList){
        List<TransactionInfoVO> transactionInfoVOList = new ArrayList<TransactionInfoVO>();
        for(TransactionInfoDTO transactionInfoDTO: transactionInfoDTOList){
            TransactionInfoVO transactionInfoVO = new TransactionInfoVO();


            transactionInfoVO.setTransactionId(transactionInfoDTO.getTransactionId());
            transactionInfoVO.setTransactionTime(transactionInfoDTO.getTransactionTime());
            transactionInfoVO.setTransactionType(transactionInfoDTO.getTransactionType());
            transactionInfoVO.setCr(transactionInfoDTO.getCr());
            transactionInfoVO.setDr(transactionInfoDTO.getDr());
            transactionInfoVO.setBalance(transactionInfoDTO.getBalance());
            transactionInfoVO.setDeposits(transactionInfoDTO.getDeposits());
            transactionInfoVO.setWithdrawals(transactionInfoDTO.getWithdrawals());
            transactionInfoVO.setTransferDeduction(transactionInfoDTO.getTransferDeduction());
            transactionInfoVO.setTransferDeposit(transactionInfoDTO.getTransferDeposit());
            transactionInfoVO.setInterest(transactionInfoDTO.getInterest());
            transactionInfoVO.setAtmLocation(transactionInfoDTO.getAtmLocation());

            transactionInfoVOList.add(transactionInfoVO);
        }
        return transactionInfoVOList;
    }

    default UserTransactionHistoryOutputVO userTransactionHistoryOutputDTOtoVO(UserTransactionHistoryOutputDTO userTransactionHistoryOutputDTO){
        UserTransactionHistoryOutputVO userTransactionHistoryOutputVO = new UserTransactionHistoryOutputVO();
        userTransactionHistoryOutputVO.setGetOfficialDataVO(getOfficialDataDTOtoVO(userTransactionHistoryOutputDTO.getGetOfficialDataDTO()));
        userTransactionHistoryOutputVO.setTransactionInfoVOList(transactionInfoListDTOtoVO(userTransactionHistoryOutputDTO.getTransactionInfoDTOList()));
        return userTransactionHistoryOutputVO;
    }

    default UserTransactionHistoryOutputDTO userTransactionHistoryOutputVOtoDTO(UserTransactionHistoryOutputVO userTransactionHistoryOutputVO){
        UserTransactionHistoryOutputDTO userTransactionHistoryOutputDTO = new UserTransactionHistoryOutputDTO();
        userTransactionHistoryOutputDTO.setGetOfficialDataDTO(getOfficialDataVOtoDTO(userTransactionHistoryOutputVO.getGetOfficialDataVO()));
        userTransactionHistoryOutputDTO.setTransactionInfoDTOList(transactionInfoListVOtoDTO(userTransactionHistoryOutputVO.getTransactionInfoVOList()));
        return userTransactionHistoryOutputDTO;
    }
}
