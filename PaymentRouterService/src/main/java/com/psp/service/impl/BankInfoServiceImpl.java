package com.psp.service.impl;

import com.psp.entity.TdBankMessageInfo;
import com.psp.mapper.TdBankMessageInfoMapper;
import com.psp.service.BankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankInfoServiceImpl implements BankInfoService {

    @Autowired
    private TdBankMessageInfoMapper tdBankMessageInfoMapper;

    @Override
    public TdBankMessageInfo queryBankInfo(String bankId) {
        return tdBankMessageInfoMapper.queryBankInfo(bankId);
    }
}
