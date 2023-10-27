package com.psp.service;

import com.psp.entity.TdTxService;

public interface CallBackUrlService {

    void addService(TdTxService tdTxService);

    void removeService(TdTxService tdTxService);

    void changeServiceUrl(TdTxService tdTxService);

    String queryCollectUrl(String serName);

    String queryCallBackUrl(String serName);

}
