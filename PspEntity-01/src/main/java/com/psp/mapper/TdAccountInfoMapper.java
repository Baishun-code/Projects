package com.psp.mapper;

import com.psp.entity.TdAccountInfo;
import org.apache.ibatis.annotations.Param;

public interface TdAccountInfoMapper {
    int insert(TdAccountInfo record);

    int insertSelective(TdAccountInfo record);

    void updateAcctToCertainFlag(@Param("acctId") String acctId,
                                 @Param("targetFlag") String targetFlag);
}