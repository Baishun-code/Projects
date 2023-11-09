package com.psp.service.impl;

import com.psp.entity.TdAccountInfo;
import com.psp.mapper.TdAccountInfoMapper;
import com.psp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private TdAccountInfoMapper tdAccountInfoMapper;

    @Override
    public void openAcct(TdAccountInfo tdAccountInfo) {
        tdAccountInfoMapper.insert(tdAccountInfo);
    }

    @Override
    public void closeAcct(String acctId) {
        tdAccountInfoMapper.updateAcctToCertainFlag(acctId, "99");
    }

    @Override
    public void lockAcct(String acctId) {
        tdAccountInfoMapper.updateAcctToCertainFlag(acctId, "01");
    }
}
