package com.psp.service;


public interface BalanceService {

    void deductFromAcct(String acctId, String currcd, double amt);

    void topUpToAcct(String acctId, String currcd, double amt);

}
