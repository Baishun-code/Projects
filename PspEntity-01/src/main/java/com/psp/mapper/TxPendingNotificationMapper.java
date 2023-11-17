package com.psp.mapper;

import com.psp.entity.TxPendingNotification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TxPendingNotificationMapper {
    int insert(TxPendingNotification record);

    int insertSelective(TxPendingNotification record);

    List<TxPendingNotification> queryAllPendingMessage();

    void deleteFinishedMessage(@Param("serialNo") Integer serialNo);
}