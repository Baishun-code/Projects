package com.psp.service.impl;


import com.psp.entity.TdTxService;
import com.psp.mapper.TdTxServiceMapper;
import com.psp.service.ScheduleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallBackUrlServiceImpl implements ScheduleNameService {

    @Autowired
    private TdTxServiceMapper tdTxServiceMapper;

    @Override
    public void addService(TdTxService tdTxService) {

    }

    @Override
    public void removeService(TdTxService tdTxService) {

    }

    @Override
    public void changeServiceUrl(TdTxService tdTxService) {

    }

    @Override
    public TdTxService queryCollectUrl(String serName) {
        return tdTxServiceMapper.queryBySerName(serName, "0");
    }

    @Override
    public TdTxService queryCallBackUrl(String serName) {
        return tdTxServiceMapper.queryBySerName(serName, "1");
    }

    @Override
    public List<TdTxService> getAllFetchTdTx() {
        return tdTxServiceMapper.queryAllCollectEntity();
    }

}
