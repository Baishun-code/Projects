package com.psp.service.impl;


import com.psp.entity.TdTxService;
import com.psp.mapper.TdTxServiceMapper;
import com.psp.service.CallBackUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallBackUrlServiceImpl implements CallBackUrlService {

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
    public String queryCollectUrl(String serName) {
        return tdTxServiceMapper.queryBySerName(serName, "0");
    }

    @Override
    public String queryCallBackUrl(String serName) {
        return tdTxServiceMapper.queryBySerName(serName, "1");
    }

}
