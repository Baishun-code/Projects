package com.psp.mapper;


import com.psp.entity.TxTransactionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TxTransactionInfoMapper {
    int insert(TxTransactionInfo record);

    int insertSelective(TxTransactionInfo record);

    int deleleBySerialNo(@Param("SerialNo") String SerialNo);

    Map<String, TxTransactionInfo> queryAllRecords();
}