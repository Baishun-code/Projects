package com.psp.service;

import com.psp.entity.TdAccountInfo;

public interface AccountService {

    void openAcct(TdAccountInfo tdAccountInfo);

    void closeAcct(String acctId);

    void lockAcct(String acctId);

}
