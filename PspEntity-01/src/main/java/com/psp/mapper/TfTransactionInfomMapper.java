package com.psp.mapper;

import com.psp.entity.TfTransactionInfo;

import java.util.List;

public interface TfTransactionInfomMapper {
    int insert(TfTransactionInfo record);

    int insertSelective(TfTransactionInfo record);

    List<TfTransactionInfo> queryAllRecords();
}