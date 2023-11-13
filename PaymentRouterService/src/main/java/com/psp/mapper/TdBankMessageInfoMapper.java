package com.psp.mapper;

import com.psp.entity.TdBankMessageInfo;
import org.apache.ibatis.annotations.Param;

public interface TdBankMessageInfoMapper {
    int insert(TdBankMessageInfo record);

    int insertSelective(TdBankMessageInfo record);

    TdBankMessageInfoMapper queryBankInfo(@Param("bankId")String bankId);
}