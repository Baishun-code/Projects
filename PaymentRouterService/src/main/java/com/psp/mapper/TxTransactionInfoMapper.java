package com.psp.mapper;

import com.psp.entity.TxTransactionInfo;
import org.apache.ibatis.annotations.Param;

public interface TxTransactionInfoMapper {
    int insert(TxTransactionInfo record);

    int insertSelective(TxTransactionInfo record);

    void deleteBySerialNo(@Param("serialNo") String serialNo);
}