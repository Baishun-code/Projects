package com.psp.mapper;

import com.psp.entity.TfAcctBalance;
import org.apache.ibatis.annotations.Param;

public interface TfAcctBalanceMapper {
    int insert(TfAcctBalance record);

    int insertSelective(TfAcctBalance record);

    void deductAcctBalance(@Param("acctId") String acctId,
                           @Param("currcd") String currcd,
                           @Param("amt") double amt);

    double queryBalanceByIdCurr(@Param("acctId") String acctId,
                                @Param("currcd") String currcd);
}