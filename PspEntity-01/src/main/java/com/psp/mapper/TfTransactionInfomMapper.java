package com.psp.mapper;

import com.psp.entity.TfTransactionInfo;

public interface TfTransactionInfomMapper {
    int insert(TfTransactionInfo record);

    int insertSelective(TfTransactionInfo record);
}