package com.psp.mapper;

import com.psp.entity.TdTxService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TdTxServiceMapper {
    int insert(TdTxService record);

    int insertSelective(TdTxService record);

    TdTxService queryBySerName(@Param("serName") String serName,
                                     @Param("flag") String flag);

    List<TdTxService> queryAllCollectEntity();
}