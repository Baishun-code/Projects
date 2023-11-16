package com.psp.mapper;

import com.psp.entity.TfTransactionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TfTransactionInfomMapper {
    int insert(TfTransactionInfo record);

    int insertSelective(TfTransactionInfo record);

    List<TfTransactionInfo> queryAllRecords();

    void updateTransactionStatus(@Param("serialNo") String serialNo,
                                 @Param("statusBefore") String statusBefore,
                                 @Param("status")String status,
                                 @Param("finshTime")Date finshTime);
}