package com.psp.service;

import com.psp.entity.TdBankMessageInfo;
import org.apache.ibatis.annotations.Param;

public interface BankInfoService {

    TdBankMessageInfo queryBankInfo(String bankId);

}
