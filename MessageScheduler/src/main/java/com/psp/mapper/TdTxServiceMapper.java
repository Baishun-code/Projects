package com.psp.mapper;

import com.psp.entity.TdTxService;
import org.apache.ibatis.annotations.Param;

public interface TdTxServiceMapper {
    int insert(TdTxService record);

    int insertSelective(TdTxService record);

    String queryBySerName(@Param("serName") String serName,
                          @Param("type") String type);
}