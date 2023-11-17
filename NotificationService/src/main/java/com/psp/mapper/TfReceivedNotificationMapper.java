package com.psp.mapper;

import com.psp.entity.TfReceivedNotification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TfReceivedNotificationMapper {
    int insert(TfReceivedNotification record);

    int insertSelective(TfReceivedNotification record);

    List<TfReceivedNotification> queryAllPendingMessage();

    void cancelPendingMessage(@Param("serialNo") Integer serialNo);
}