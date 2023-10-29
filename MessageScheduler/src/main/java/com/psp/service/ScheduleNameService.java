package com.psp.service;

import com.psp.entity.TdTxService;

import java.util.List;

public interface ScheduleNameService {

    void addService(TdTxService tdTxService);

    void removeService(TdTxService tdTxService);

    void changeServiceUrl(TdTxService tdTxService);

    TdTxService queryCollectUrl(String serName);

    TdTxService queryCallBackUrl(String serName);

    List<TdTxService> getAllFetchTdTx();

}
