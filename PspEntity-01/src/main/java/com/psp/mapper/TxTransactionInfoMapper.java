package com.psp.mapper;


import com.psp.entity.TxTransactionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TxTransactionInfoMapper {
    int insert(TxTransactionInfo record);

    int insertSelective(TxTransactionInfo record);

    int deleleBySerialNo(@Param("SerialNo") String SerialNo);

    List<TxTransactionInfo> queryAllRecords();
}