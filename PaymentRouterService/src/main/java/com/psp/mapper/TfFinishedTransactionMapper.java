package com.psp.mapper;

import com.psp.entity.TfFinishedTransaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TfFinishedTransactionMapper {
    int insert(TfFinishedTransaction record);

    int insertSelective(TfFinishedTransaction record);

    List<TfFinishedTransaction> queryAllTransactions();

    void deleteTransaction(@Param("serialNo") String serialNo);
}